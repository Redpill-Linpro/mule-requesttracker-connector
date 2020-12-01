package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

import java.util.List;

public class Attachments extends RequestTrackerObject {

    private List<AttachmentSummary> Attachments;

    public List<AttachmentSummary> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<AttachmentSummary> attachments) {
        this.Attachments = attachments;
    }
}
