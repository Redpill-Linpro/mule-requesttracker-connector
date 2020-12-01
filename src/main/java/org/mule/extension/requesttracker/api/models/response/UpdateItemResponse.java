package org.mule.extension.requesttracker.api.models.response;

public class UpdateItemResponse {

    private int status;
    private String itemId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
