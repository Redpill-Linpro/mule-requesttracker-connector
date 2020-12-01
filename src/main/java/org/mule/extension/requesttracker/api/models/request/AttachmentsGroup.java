package org.mule.extension.requesttracker.api.models.request;

import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.param.Content;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public final class AttachmentsGroup {

    @Optional
    @Parameter
    @Content
    @NullSafe
    private Map<String, TypedValue<InputStream>> attachments;

    public Map<String, TypedValue<InputStream>> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, TypedValue<InputStream>> attachments) {
        this.attachments = attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttachmentsGroup that = (AttachmentsGroup) o;
        return attachments.equals(that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachments);
    }
}

