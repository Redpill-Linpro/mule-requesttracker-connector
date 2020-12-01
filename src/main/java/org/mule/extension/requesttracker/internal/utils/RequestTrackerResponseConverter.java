package org.mule.extension.requesttracker.internal.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerResponseCheckException;
import org.mule.extension.requesttracker.api.models.response.*;
import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestTrackerResponseConverter {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestTrackerResponseConverter.class);
    private final static String RT_NEWLINE = "\n";

    public static <T extends RequestTrackerObject> List<T> convertResponse(String response, Class<T> tClass) throws RequestTrackerResponseCheckException {
        if (StringUtils.isBlank(response)) {
            return new ArrayList<>(0);
        }
        List<T> objects = new ArrayList<>();
        boolean first = true;
        for (String responseTicket : response.split("--"+RT_NEWLINE)) {
            List<String> lines = getLines(responseTicket);
            if (first) {
                checkStatus(lines);
                first = false;
            }
            T ticket = convertEntry(lines, tClass);
            if (ticket != null && StringUtils.isNotBlank(ticket.getId())) {
                objects.add(ticket);
            }
        }
        return objects;
    }

    private static <T extends RequestTrackerObject> T convertEntry(List<String> lines, Class<T> tClass) {
        T obj;
        try {
            obj = tClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
        for (String line : lines) {
            String[] keyValue = line.split(":[ ("+RT_NEWLINE+")]", 2);
            if (keyValue.length > 1) {
                String key = keyValue[0];
                String value = keyValue[1];
                if (key.matches("CF\\.\\{.+}")) {
                    obj.addCustomField(key.replaceFirst("CF\\.\\{(.+)}", "$1"), value);
                } else if (StringUtils.equalsIgnoreCase("id", key)) {
                    if (value.contains("/")) {
                        obj.setId(value.split("/")[1]);
                    } else {
                        obj.setId(value);
                    }
                } else if (StringUtils.equalsIgnoreCase("Attachments", key)) {
                    try {
                        Field field = tClass.getDeclaredField(key);
                        if (field.getType().equals(List.class)) {
                            field.setAccessible(true);
                            field.set(obj, extractAttachmentSummaries(value));
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Unable to get Attachment field for class " + tClass.getName());
                    }
                } else if (StringUtils.equalsIgnoreCase("Headers", key)) {
                    try {
                        Field field = tClass.getDeclaredField(key);
                        if (field.getType().equals(AttachmentHeaders.class)) {
                            field.setAccessible(true);
                            field.set(obj, extractAttachmentHeaders(value));
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Failed to get Headers field for class " + tClass.getName());
                    }
                } else {
                    try {
                        Field field = tClass.getDeclaredField(key);
                        field.setAccessible(true);
                        if (field.getType().equals(LocalDateTime.class)) {
                            try {
                                field.set(obj, LocalDateTime.from(DateTimeFormatter.ofPattern(
                                        value.matches(".{3} .{3} \\d{2} \\d{2}:\\d{2}:\\d{2} \\d{4}") ?
                                        "EEE MMM dd HH:mm:ss yyyy" : "yyyy-MM-dd HH:mm:ss"
                                ).parse(value)));
                            } catch (Exception e) {
                                // Expected. Not set date fields can have the value "Not Set"
                            }
                        } else if (field.getType().equals(Boolean.class)) {
                            field.set(obj, "1".equals(value));
                        } else {
                            field.set(obj, value);
                        }
                    } catch (Exception e) {
                        LOGGER.warn(tClass.getName() + " did not have field: " + key);
                    }
                }
            }
        }
        return obj;
    }

    /**
     * Converts a String into a List of String based on line separators. If next line starts with a blank space, the line is added to last String instead of used as new
     * @param string the String to extract lines from
     * @return List of Strings each representing a key: value line from the Request Tracker REST format
     */
    public static List<String> getLines(String string) {
        if (StringUtils.isBlank(string)) {
            return new ArrayList<>();
        }
        List<String> lines = new ArrayList<>();
        int currentKeyLength = 1;
        int possibleExtraLineBrakes = 0;
        for (String line : string.split(RT_NEWLINE)) {
            if (line.length() == 0) {
                possibleExtraLineBrakes++;
                continue;
            }
            if (line.startsWith(" ") && !lines.isEmpty()) {
                int extraBlanks = line.matches("^ {" + currentKeyLength + "}.+") ? currentKeyLength : 1;
                lines.set(lines.size()-1,
                        lines.get(lines.size()-1) +
                                StringUtils.repeat(System.lineSeparator(), possibleExtraLineBrakes + 1) +
                                line.substring(extraBlanks));
            } else {
                currentKeyLength = line.split(":")[0].length() + 2;
                possibleExtraLineBrakes = 0;
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Checks if the first String in a list of String contains the required Request Tracker header
     * Some error responses from Request Tracker return Status Code 200 even when things go wrong. So we look for other error indicators also.
     * @param lines The lines from a Request Tracker REST api response
     * @throws RequestTrackerResponseCheckException If response lines indicated an error
     */
    public static int checkStatus(List<String> lines) throws RequestTrackerResponseCheckException {
        if (lines.isEmpty()) throw new RequestTrackerResponseCheckException("Response was empty");
        int statusCode = extractStatusCodeFromLine(lines.get(0));
        if (statusCode < 200 || statusCode > 299) {
            throw new RequestTrackerResponseCheckException(lines, statusCode);
        }
        if (lines.stream().anyMatch(s -> s.matches(
                "(^# Could not create .+)|" +
                        "(^# Required: .+)"))) {
            throw new RequestTrackerResponseCheckException(lines, 400);
        }
        if (lines.stream().anyMatch(s -> s.matches(
                "(^# .+ \\d+ does not exist.)|" +
                        "(^# Invalid attachment id: \\d+)|" +
                        "(^# Transaction \\d+ is not related to Ticket \\d+)"))) {
            throw new RequestTrackerResponseCheckException(lines, 404);
        }
        return statusCode;
    }

    private static int extractStatusCodeFromLine(String line) {
        return NumberUtils.toInt(line.replaceAll(".* ([0-9]{3}) .*", "$1"), -1);
    }

    private static List<AttachmentSummary> extractAttachmentSummaries(String value) {
        if (StringUtils.isBlank(value)) {
            return new ArrayList<>(0);
        }
        List<AttachmentSummary> attachmentSummaries = new ArrayList<>();
        List<String> summaryLines = getLines(value);
        for (String summaryLine : summaryLines) {
            Pattern pattern = Pattern.compile("(\\d+): (.+) \\((.+) / (.+)\\),?$");
            Matcher matcher = pattern.matcher(summaryLine);
            if (matcher.find()) {
                AttachmentSummary summary = new AttachmentSummary();
                summary.setId(matcher.group(1));
                summary.setName(matcher.group(2));
                summary.setContentType(matcher.group(3));
                summary.setSize(matcher.group(4));
                attachmentSummaries.add(summary);
            } else {
                pattern = Pattern.compile("(\\d+): (.+) \\((.+)\\),?$");
                matcher = pattern.matcher(summaryLine);
                if (matcher.find()) {
                    AttachmentSummary summary = new AttachmentSummary();
                    summary.setId(matcher.group(1));
                    summary.setName(matcher.group(2));
                    summary.setSize(matcher.group(3));
                    attachmentSummaries.add(summary);
                }
            }
        }
        return attachmentSummaries;
    }

    private static AttachmentHeaders extractAttachmentHeaders(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        AttachmentHeaders attachmentHeaders = new AttachmentHeaders();
        List<String> headers = getLines(value.replaceAll(" {8}", ""));
        for (String headerString : headers) {
            Pattern pattern = Pattern.compile("(.+): (.+)$", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(headerString);
            if (matcher.find()) {
                String headerFieldName = StringUtils.trim(matcher.group(1).replaceAll("-", ""));
                try {
                    Field field = AttachmentHeaders.class.getDeclaredField(headerFieldName);
                    field.setAccessible(true);
                    field.set(attachmentHeaders, matcher.group(2));
                } catch (Exception e) {
                    LOGGER.warn("Could not find header field " + headerFieldName + " in " + AttachmentHeaders.class.getName());
                }
            }
        }
        return attachmentHeaders;
    }

    public static AttachmentContent convertAttachmentContentResponse(String response) {
        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setContent(response.replaceFirst(".+\\R.+\\R", ""));
        return attachmentContent;
    }

    public static List<HistorySummary> convertHistorySummaryResponse(String response) {
        if (StringUtils.isBlank(response)) {
            return new ArrayList<>(0);
        }
        List<String> lines = getLines(response);
        checkStatus(lines);
        List<HistorySummary> summaries = new ArrayList<>();
        for(String line : lines) {
            String[] pair = line.split(": ", 2);
            if (pair.length > 1) {
                summaries.add(new HistorySummary(pair[0], pair[1]));
            }
        }
        return summaries;
    }

    public static UpdateItemResponse convertCreateTicketResponse(String response) {
        return convertCreateItemResponse(response, "# Ticket (\\d+) created.");
    }

    public static UpdateItemResponse convertEditTicketResponse(String response) {
        return convertCreateItemResponse(response, "# Ticket (\\d+) updated.");
    }

    public static UpdateItemResponse convertEditTicketLinksResponse(String response) {
        return convertCreateItemResponse(response, "# Links for ticket (\\d+) updated.");
    }
    
    public static UpdateItemResponse convertCreateUserResponse(String response) {
        return convertCreateItemResponse(response, "# User (\\d+) created.");
    }

    public static UpdateItemResponse convertEditUserResponse(String response) {
        return convertCreateItemResponse(response, "# User (\\d+) updated.");
    }

    private static UpdateItemResponse convertCreateItemResponse(String response, String pattern) {
        List<String> lines = getLines(response);

        UpdateItemResponse updateItemResponse = new UpdateItemResponse();
        updateItemResponse.setStatus(checkStatus(lines));

        Pattern cPattern = Pattern.compile(pattern);
        for (String line : lines) {
            Matcher matcher = cPattern.matcher(line);
            if (matcher.find()) {
                updateItemResponse.setItemId(matcher.group(1));
                return updateItemResponse;
            }
        }
        return updateItemResponse;
    }

    public static UpdateResponse convertSimpleResponseCodeResponse(String response) throws RequestTrackerResponseCheckException {
        UpdateResponse updateResponse = new UpdateResponse();
        if (StringUtils.isBlank(response)) {
            updateResponse.setStatus(500);
            return updateResponse;
        }
        List<String> lines = getLines(response);
        updateResponse.setStatus(checkStatus(lines));
        return updateResponse;
    }
}
