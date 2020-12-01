package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

import java.time.LocalDateTime;
import java.util.List;

public class HistoryEntry extends RequestTrackerObject {
    private String Ticket;
    private String TimeTaken;
    private String Type;
    private String Field;
    private String OldValue;
    private String NewValue;
    private String Data;
    private String Description;
    private String Content;
    private String Creator;
    private LocalDateTime Created;

    private List<AttachmentSummary> Attachments;

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getTimeTaken() {
        return TimeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        TimeTaken = timeTaken;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getOldValue() {
        return OldValue;
    }

    public void setOldValue(String oldValue) {
        OldValue = oldValue;
    }

    public String getNewValue() {
        return NewValue;
    }

    public void setNewValue(String newValue) {
        NewValue = newValue;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public LocalDateTime getCreated() {
        return Created;
    }

    public void setCreated(LocalDateTime created) {
        Created = created;
    }

    public List<AttachmentSummary> getAttachments() {
        return Attachments;
    }

    public void setAttachments(List<AttachmentSummary> attachments) {
        Attachments = attachments;
    }
}
