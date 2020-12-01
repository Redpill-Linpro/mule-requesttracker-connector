package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

import java.time.LocalDateTime;

public class Ticket extends RequestTrackerObject {
    private String Queue;
    private String Owner;
    private String Creator;
    private String Subject;
    private String Status;
    private String Priority;
    private String InitialPriority;
    private String FinalPriority;
    private String Requestors;
    private String Cc;
    private String AdminCc;
    private LocalDateTime Created;
    private LocalDateTime Starts;
    private LocalDateTime Started;
    private LocalDateTime Due;
    private LocalDateTime Resolved;
    private LocalDateTime Told;
    private LocalDateTime LastUpdated;
    private String TimeEstimated;
    private String TimeWorked;
    private String TimeLeft;

    public String getQueue() {
        return Queue;
    }

    public void setQueue(String queue) {
        Queue = queue;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getInitialPriority() {
        return InitialPriority;
    }

    public void setInitialPriority(String initialPriority) {
        InitialPriority = initialPriority;
    }

    public String getFinalPriority() {
        return FinalPriority;
    }

    public void setFinalPriority(String finalPriority) {
        FinalPriority = finalPriority;
    }

    public String getRequestors() {
        return Requestors;
    }

    public void setRequestors(String requestors) {
        Requestors = requestors;
    }

    public String getCc() {
        return Cc;
    }

    public void setCc(String cc) {
        Cc = cc;
    }

    public String getAdminCc() {
        return AdminCc;
    }

    public void setAdminCc(String adminCc) {
        AdminCc = adminCc;
    }

    public LocalDateTime getCreated() {
        return Created;
    }

    public void setCreated(LocalDateTime created) {
        Created = created;
    }

    public LocalDateTime getStarts() {
        return Starts;
    }

    public void setStarts(LocalDateTime starts) {
        Starts = starts;
    }

    public LocalDateTime getStarted() {
        return Started;
    }

    public void setStarted(LocalDateTime started) {
        Started = started;
    }

    public LocalDateTime getDue() {
        return Due;
    }

    public void setDue(LocalDateTime due) {
        Due = due;
    }

    public LocalDateTime getResolved() {
        return Resolved;
    }

    public void setResolved(LocalDateTime resolved) {
        Resolved = resolved;
    }

    public LocalDateTime getTold() {
        return Told;
    }

    public void setTold(LocalDateTime told) {
        Told = told;
    }

    public LocalDateTime getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public String getTimeEstimated() {
        return TimeEstimated;
    }

    public void setTimeEstimated(String timeEstimated) {
        TimeEstimated = timeEstimated;
    }

    public String getTimeWorked() {
        return TimeWorked;
    }

    public void setTimeWorked(String timeWorked) {
        TimeWorked = timeWorked;
    }

    public String getTimeLeft() {
        return TimeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        TimeLeft = timeLeft;
    }
}
