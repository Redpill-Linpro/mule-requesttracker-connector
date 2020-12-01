package org.mule.extension.requesttracker.api.models.request;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Text;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class UpdateTicket {

    @Parameter
    @Optional
    private String queue;
    @Parameter
    @Optional
    private String requestor;
    @Parameter
    @Optional
    private String subject;
    @Parameter
    @Optional
    private String cc;
    @Parameter
    @Optional
    private String adminCc;
    @Parameter
    @Optional
    private String owner;
    @Parameter
    @Optional
    private String status;
    @Parameter
    @Optional
    private String priority;
    @Parameter
    @Optional
    private String initialPriority;
    @Parameter
    @Optional
    private String finalPriority;
    @Parameter
    @Optional
    private String timeEstimated;
    @Parameter
    @Optional
    private LocalDateTime starts;
    @Parameter
    @Optional
    private LocalDateTime due;
    @Parameter
    @Optional
    @Text
    private String text;
    @Parameter
    @Optional
    private Map<String, String> customFields;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRequestor() {
        return requestor;
    }

    public void setRequestor(String requestor) {
        this.requestor = requestor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getAdminCc() {
        return adminCc;
    }

    public void setAdminCc(String adminCc) {
        this.adminCc = adminCc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getInitialPriority() {
        return initialPriority;
    }

    public void setInitialPriority(String initialPriority) {
        this.initialPriority = initialPriority;
    }

    public String getFinalPriority() {
        return finalPriority;
    }

    public void setFinalPriority(String finalPriority) {
        this.finalPriority = finalPriority;
    }

    public String getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(String timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public LocalDateTime getStarts() {
        return starts;
    }

    public void setStarts(LocalDateTime starts) {
        this.starts = starts;
    }

    public LocalDateTime getDue() {
        return due;
    }

    public void setDue(LocalDateTime due) {
        this.due = due;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, String> customFields) {
        this.customFields = customFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateTicket that = (UpdateTicket) o;
        return Objects.equals(queue, that.queue) &&
                Objects.equals(requestor, that.requestor) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(cc, that.cc) &&
                Objects.equals(adminCc, that.adminCc) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(status, that.status) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(initialPriority, that.initialPriority) &&
                Objects.equals(finalPriority, that.finalPriority) &&
                Objects.equals(timeEstimated, that.timeEstimated) &&
                Objects.equals(starts, that.starts) &&
                Objects.equals(due, that.due) &&
                Objects.equals(text, that.text) &&
                Objects.equals(customFields, that.customFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queue, requestor, subject, cc, adminCc, owner, status, priority, initialPriority, finalPriority, timeEstimated, starts, due, text, customFields);
    }
}
