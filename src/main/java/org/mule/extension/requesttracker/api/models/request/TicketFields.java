package org.mule.extension.requesttracker.api.models.request;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import java.util.List;
import java.util.Objects;

public class TicketFields implements Fields  {

    @Parameter
    @Optional
    private boolean queue;

    @Parameter
    @Optional
    private boolean owner;

    @Parameter
    @Optional
    private boolean creator;

    @Parameter
    @Optional
    private boolean subject;

    @Parameter
    @Optional
    private boolean status;

    @Parameter
    @Optional
    private boolean priority;

    @Parameter
    @Optional
    private boolean initialPriority;

    @Parameter
    @Optional
    private boolean finalPriority;

    @Parameter
    @Optional
    private boolean requestors;

    @Parameter
    @Optional
    private boolean cc;

    @Parameter
    @Optional
    private boolean adminCc;

    @Parameter
    @Optional
    private boolean created;

    @Parameter
    @Optional
    private boolean starts;

    @Parameter
    @Optional
    private boolean started;

    @Parameter
    @Optional
    private boolean due;

    @Parameter
    @Optional
    private boolean resolved;

    @Parameter
    @Optional
    private boolean told;

    @Parameter
    @Optional
    private boolean lastUpdated;

    @Parameter
    @Optional
    private boolean timeEstimated;

    @Parameter
    @Optional
    private boolean timeWorked;

    @Parameter
    @Optional
    private boolean timeLeft;

    @Parameter
    @Optional
    @DisplayName("Custom Fields")
    private List<String> _customFields;

    public boolean getQueue() {
        return queue;
    }

    public void setQueue(boolean queue) {
        this.queue = queue;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public boolean getCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean getSubject() {
        return subject;
    }

    public void setSubject(boolean subject) {
        this.subject = subject;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean getInitialPriority() {
        return initialPriority;
    }

    public void setInitialPriority(boolean initialPriority) {
        this.initialPriority = initialPriority;
    }

    public boolean getFinalPriority() {
        return finalPriority;
    }

    public void setFinalPriority(boolean finalPriority) {
        this.finalPriority = finalPriority;
    }

    public boolean getRequestors() {
        return requestors;
    }

    public void setRequestors(boolean requestors) {
        this.requestors = requestors;
    }

    public boolean getCc() {
        return cc;
    }

    public void setCc(boolean cc) {
        this.cc = cc;
    }

    public boolean getAdminCc() {
        return adminCc;
    }

    public void setAdminCc(boolean adminCc) {
        this.adminCc = adminCc;
    }

    public boolean getCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public boolean getStarts() {
        return starts;
    }

    public void setStarts(boolean starts) {
        this.starts = starts;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean getDue() {
        return due;
    }

    public void setDue(boolean due) {
        this.due = due;
    }

    public boolean getResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean getTold() {
        return told;
    }

    public void setTold(boolean told) {
        this.told = told;
    }

    public boolean getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(boolean lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public boolean getTimeEstimated() {
        return timeEstimated;
    }

    public void setTimeEstimated(boolean timeEstimated) {
        this.timeEstimated = timeEstimated;
    }

    public boolean getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(boolean timeWorked) {
        this.timeWorked = timeWorked;
    }

    public boolean getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(boolean timeLeft) {
        this.timeLeft = timeLeft;
    }

    public List<String> get_customFields() {
        return _customFields;
    }

    public void set_customFields(List<String> _customFields) {
        this._customFields = _customFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketFields that = (TicketFields) o;
        return queue == that.queue &&
                owner == that.owner &&
                creator == that.creator &&
                subject == that.subject &&
                status == that.status &&
                priority == that.priority &&
                initialPriority == that.initialPriority &&
                finalPriority == that.finalPriority &&
                requestors == that.requestors &&
                cc == that.cc &&
                adminCc == that.adminCc &&
                created == that.created &&
                starts == that.starts &&
                started == that.started &&
                due == that.due &&
                resolved == that.resolved &&
                told == that.told &&
                lastUpdated == that.lastUpdated &&
                timeEstimated == that.timeEstimated &&
                timeWorked == that.timeWorked &&
                timeLeft == that.timeLeft &&
                Objects.equals(_customFields, that._customFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queue, owner, creator, subject, status, priority, initialPriority, finalPriority, requestors, cc, adminCc, created, starts, started, due, resolved, told, lastUpdated, timeEstimated, timeWorked, timeLeft, _customFields);
    }
}
