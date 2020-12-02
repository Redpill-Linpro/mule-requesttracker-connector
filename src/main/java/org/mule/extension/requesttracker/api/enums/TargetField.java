package org.mule.extension.requesttracker.api.enums;

public enum TargetField {
    CREATED ("Created"),
    UPDATED ("LastUpdated"),
    RESOLVED ("Resolved"),
    STARTED ("Started");

    private final String fieldName;

    TargetField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
