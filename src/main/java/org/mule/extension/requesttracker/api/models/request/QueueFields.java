package org.mule.extension.requesttracker.api.models.request;

import org.mule.extension.requesttracker.internal.annotations.RTName;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import java.util.Objects;

public class QueueFields implements Fields {

    @Parameter @Optional @RTName("Name")
    private boolean queueName;
    @Parameter @Optional
    private boolean description;
    @Parameter @Optional
    private boolean correspondAddress;
    @Parameter @Optional
    private boolean commentAddress;
    @Parameter @Optional
    private boolean initialPriority;
    @Parameter @Optional
    private boolean finalPriority;
    @Parameter @Optional
    private boolean defaultDueIn;

    public boolean isQueueName() {
        return queueName;
    }

    public void setQueueName(boolean queueName) {
        this.queueName = queueName;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public boolean isCorrespondAddress() {
        return correspondAddress;
    }

    public void setCorrespondAddress(boolean correspondAddress) {
        this.correspondAddress = correspondAddress;
    }

    public boolean isCommentAddress() {
        return commentAddress;
    }

    public void setCommentAddress(boolean commentAddress) {
        this.commentAddress = commentAddress;
    }

    public boolean isInitialPriority() {
        return initialPriority;
    }

    public void setInitialPriority(boolean initialPriority) {
        this.initialPriority = initialPriority;
    }

    public boolean isFinalPriority() {
        return finalPriority;
    }

    public void setFinalPriority(boolean finalPriority) {
        this.finalPriority = finalPriority;
    }

    public boolean isDefaultDueIn() {
        return defaultDueIn;
    }

    public void setDefaultDueIn(boolean defaultDueIn) {
        this.defaultDueIn = defaultDueIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueFields that = (QueueFields) o;
        return queueName == that.queueName &&
                description == that.description &&
                correspondAddress == that.correspondAddress &&
                commentAddress == that.commentAddress &&
                initialPriority == that.initialPriority &&
                finalPriority == that.finalPriority &&
                defaultDueIn == that.defaultDueIn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(queueName, description, correspondAddress, commentAddress, initialPriority, finalPriority, defaultDueIn);
    }
}
