package org.mule.extension.requesttracker.internal.models;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestTrackerObject {
    private String id;
    private Map<String, String> customFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    public void addCustomField(String key, String value) {
        if (customFields == null) {
            customFields = new HashMap<>();
        }
        this.customFields.put(key, value);
    }
}
