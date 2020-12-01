package org.mule.extension.requesttracker.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.extension.requesttracker.api.enums.CommentContentType;
import org.mule.extension.requesttracker.api.models.request.UpdateTicketLinks;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerRequestConverter;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class RequestTrackerRequestConverterTest {

    @Test
    public void testConvertToRequestTrackerBodySkipNullValues() {
        String value = "bo@account.com";
        UpdateTicketLinks updateTicketLinks = new UpdateTicketLinks();
        updateTicketLinks.setDependedOnBy(value);
        String result = RequestTrackerRequestConverter.convertToRequestTrackerBody(updateTicketLinks, UpdateTicketLinks.class, true);
        assertEquals("DependedOnBy: " + value,result);
    }

    @Test
    public void testConvertToRequestTrackerBodyNullValues() {
        String result = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleClass("Test", null), SimpleClass.class);
        assertEquals("String1: Test\nString2: ", result);
    }

    @Test
    public void testConvertToRequestTrackerBodyMultiline() {
        String result = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleClass("Test" + System.lineSeparator() + "test2", ""), SimpleClass.class);
        assertEquals("String1: Test\n test2\nString2: ", result);
    }

    @Test
    public void testConvertToRequestTrackerBodyCustomFields() {
        CustomFields customFields = new CustomFields();
        customFields.put("testKey","testValue");
        String result = RequestTrackerRequestConverter.convertToRequestTrackerBody(customFields, CustomFields.class);
        assertEquals("CF-testKey: testValue", result);
    }

    @Test
    public void testConvertToRequestTrackerBodyBoolean() {
        String trueResult = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleBooleanClass(true), SimpleBooleanClass.class);
        String falseResult = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleBooleanClass(false), SimpleBooleanClass.class);
        assertEquals("Bool: 1", trueResult);
        assertEquals("Bool: 0", falseResult);
    }

    @Test
    public void testConvertToRequestTrackerBodyLocalDateTime() {
        String result = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleLocalDateTimeClass(LocalDateTime.of(2020, Month.DECEMBER, 1, 1, 1)), SimpleLocalDateTimeClass.class);
        assertEquals("LocalDateTime: 2020-12-01 01:01:00", result);
    }

    @Test
    public void testConvertToRequestTrackerBodyCommentContentType() {
        String htmlResult = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleCommentContentType(CommentContentType.HTML), SimpleCommentContentType.class);
        String textResult = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleCommentContentType(CommentContentType.TEXT), SimpleCommentContentType.class);
        assertEquals("Content-Type: " + CommentContentType.HTML.getRtValue(), htmlResult);
        assertEquals("", textResult);
    }

    @Test
    public void testConvertToRequestTrackerBodyFileList() {
        SimpleFileSetClass simpleFileSetClass = new SimpleFileSetClass();
        simpleFileSetClass.add("file1");
        simpleFileSetClass.add("file2");
        String nonEmptyResult = RequestTrackerRequestConverter.convertToRequestTrackerBody(simpleFileSetClass, SimpleFileSetClass.class);
        String emptyResult = RequestTrackerRequestConverter.convertToRequestTrackerBody(new SimpleFileSetClass(), SimpleFileSetClass.class);
        assertEquals("Attachment: file2\n file1", nonEmptyResult);
        assertEquals("Attachment: ", emptyResult);
    }

    public static class SimpleClass {
        private String string1;
        private String string2;

        public SimpleClass(String string1, String string2) {
            this.string1 = string1;
            this.string2 = string2;
        }

        public String getString1() {
            return string1;
        }

        public void setString1(String string1) {
            this.string1 = string1;
        }

        public String getString2() {
            return string2;
        }

        public void setString2(String string2) {
            this.string2 = string2;
        }
    }

    public static class CustomFields {
        private Map<String, String> customFields = new HashMap<>();

        public void put(String key, String value) {
            this.customFields.put(key, value);
        }

        public Map<String, String> getCustomFields() {
            return customFields;
        }

        public void setCustomFields(Map<String, String> customFields) {
            this.customFields = customFields;
        }
    }

    public static class SimpleBooleanClass {
        private Boolean bool;

        public SimpleBooleanClass(Boolean bool) {
            this.bool = bool;
        }

        public Boolean isBool() {
            return bool;
        }

        public void setBool(Boolean bool) {
            this.bool = bool;
        }
    }

    public static class SimpleLocalDateTimeClass {
        private LocalDateTime localDateTime;

        public SimpleLocalDateTimeClass(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }

        public LocalDateTime getLocalDateTime() {
            return localDateTime;
        }

        public void setLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
        }
    }

    public static class SimpleCommentContentType {
        private CommentContentType type;

        public SimpleCommentContentType(CommentContentType type) {
            this.type = type;
        }

        public CommentContentType getType() {
            return type;
        }

        public void setType(CommentContentType type) {
            this.type = type;
        }
    }

    public static class SimpleFileSetClass {
        private Set<String> fileNames = new HashSet<>();

        public void add(String fileName) {
            this.fileNames.add(fileName);
        }

        public Set<String> getFileNames() {
            return fileNames;
        }

        public void setFileNames(Set<String> fileNames) {
            this.fileNames = fileNames;
        }
    }
}
