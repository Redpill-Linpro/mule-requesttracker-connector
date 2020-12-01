package org.mule.extension.requesttracker.api.enums;

public enum CommentAction {
    COMMENT ("comment"),
    CORRESPOND ("correspond");

    private final String rtValue;

    CommentAction(String rtValue) {
        this.rtValue = rtValue;
    }

    public String getRtValue() {
        return rtValue;
    }
}
