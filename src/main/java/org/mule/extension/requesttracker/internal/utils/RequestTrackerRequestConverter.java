package org.mule.extension.requesttracker.internal.utils;

import org.apache.commons.lang3.StringUtils;
import org.mule.extension.requesttracker.api.enums.CommentAction;
import org.mule.extension.requesttracker.api.enums.CommentContentType;
import org.mule.extension.requesttracker.internal.annotations.RTName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RequestTrackerRequestConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestTrackerRequestConverter.class);
    private static final String RT_NEWLINE = "\n";

    public static String convertToRequestTrackerBody(Object obj, Class<?> tClass) {
        return convertToRequestTrackerBody(obj, tClass, false);
    }

    /**
     * Converts any object to the content format used by Request Tracker REST 1.0 POST requests
     *
     * @param obj            The object to convert
     * @param tClass         Class of object. We cannot rely on extracting it from obj, as Mule sometimes enhances the class with Enhancer from CGLib
     * @param skipNullValues true if null values should exclude the entire key/value pair from the result
     * @return A String with the converted object. Ready to be used as content in a multipart entity
     */
    public static String convertToRequestTrackerBody(Object obj, Class<?> tClass, boolean skipNullValues) {
        if (obj == null) {
            return "";
        }

        Field[] fields = tClass.getDeclaredFields();
        List<String> lines = new ArrayList<>();
        try {
            for (Field field : fields) {
                String name = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(name, tClass);
                if (field.isAnnotationPresent(RTName.class)) {
                    name = field.getAnnotation(RTName.class).value();
                }
                if (!"id".equals(name)) {
                    name = StringUtils.capitalize(name);
                }
                field.setAccessible(true);
                Object value = pd.getReadMethod().invoke(obj);
                if (value == null) {
                    if (skipNullValues) {
                        continue;
                    }
                }
                Class<?> fieldType = field.getType();
                LOGGER.info("Adding a " + fieldType.getName() + " with name: " + name);
                if (fieldType.equals(String.class)) {
                    String stringValue = handleMultiline((String) value);
                    lines.add(name + ": " + stringValue);
                } else if (fieldType.equals(Map.class)) {
                    if (value == null) {
                        continue;
                    }
                    Map<String, String> valueMap = (Map<String, String>) value;
                    for (String key : valueMap.keySet()) {
                        lines.add("CF-" + key + ": " + handleMultiline(valueMap.get(key)));
                    }
                } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    lines.add(name + ": " + (value != null && (Boolean) value ? "1" : "0"));
                } else if (fieldType.equals(LocalDateTime.class)) {
                    lines.add(name + ": " + (value == null ? "" : DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format((LocalDateTime) value)));
                } else if (fieldType.equals(CommentContentType.class)) {
                    CommentContentType contentType = (CommentContentType) value;
                    if (contentType.equals(CommentContentType.HTML)) {
                        lines.add("Content-Type: " + contentType.getRtValue());
                    }
                } else if (fieldType.equals(CommentAction.class)) {
                    lines.add("Action: " + (value == null ? "" : ((CommentAction) value).getRtValue()));
                } else if (fieldType.equals(Set.class) && StringUtils.equalsIgnoreCase(name, "fileNames")) {
                    if (value == null) {
                        value = new HashSet<String>();
                    }
                    Set<String> files = (HashSet<String>) value;
                    if (files.isEmpty()) {
                        files.add("");
                    }
                    boolean first = true;
                    for (String fileName : files.stream().filter(Objects::nonNull).collect(Collectors.toList())) {
                        if (first) {
                            lines.add("Attachment: " + fileName);
                            first = false;
                        } else {
                            lines.add(" " + fileName);
                        }
                    }
                } else {
                    lines.add(name + ": " + (value == null ? "" : value.toString()));
                }
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            LOGGER.error("Failed to convert object of type " + obj.getClass().getSimpleName(), e);
        }
        return StringUtils.join(lines, RT_NEWLINE);
    }

    /**
     * Adds relevant spaces to extra lines in multiline values
     *
     * @param string String to be modified
     * @return Modified String
     */
    private static String handleMultiline(String string) {
        if (string == null) {
            return "";
        }
        if (string.contains(System.lineSeparator())) {
            return string.replaceAll(System.lineSeparator(), RT_NEWLINE + " ");
        }
        return string;
    }

}
