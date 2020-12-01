package org.mule.extension.requesttracker.api.enums;

public enum CommentContentType {
    TEXT ("text/plan"),
    HTML ("text/html");

    private final String rtValue;

    CommentContentType(String rtValue) {
        this.rtValue = rtValue;
    }

    public String getRtValue() {
        return rtValue;
    }
}
