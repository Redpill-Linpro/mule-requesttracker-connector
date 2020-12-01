package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

import java.time.LocalDateTime;

public class Attachment extends RequestTrackerObject {
    private String Subject; // MulePromote.png
    private String Creator; // 14
    private LocalDateTime Created; // 2020-11-06 07:35:55
    private String Transaction; // 248
    private String Parent; // 116
    private String MessageId; //
    private String Filename; // MulePromote.png
    private String ContentType; // image/png
    private String ContentEncoding; // base64

    private AttachmentHeaders Headers;

    private String Content;

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
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

    public String getTransaction() {
        return Transaction;
    }

    public void setTransaction(String transaction) {
        Transaction = transaction;
    }

    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        Parent = parent;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

    public String getContentEncoding() {
        return ContentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        ContentEncoding = contentEncoding;
    }

    public AttachmentHeaders getHeaders() {
        return Headers;
    }

    public void setHeaders(AttachmentHeaders headers) {
        Headers = headers;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
