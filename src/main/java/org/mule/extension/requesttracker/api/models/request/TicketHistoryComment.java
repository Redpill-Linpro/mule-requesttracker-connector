package org.mule.extension.requesttracker.api.models.request;

import org.mule.extension.requesttracker.api.enums.CommentAction;
import org.mule.extension.requesttracker.api.enums.CommentContentType;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Text;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TicketHistoryComment {
    @Parameter
    @DisplayName("Ticket ID")
    private String id;
    @Parameter
    @Optional(defaultValue = "COMMENT")
    private CommentAction action;
    @Parameter
    @Optional(defaultValue = "TEXT")
    @DisplayName("Content Type")
    private CommentContentType contentType;
    @Parameter
    @Text
    private String text;
    private Set<String> fileNames;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CommentAction getAction() {
        return action;
    }

    public void setAction(CommentAction action) {
        this.action = action;
    }

    public CommentContentType getContentType() {
        return contentType;
    }

    public void setContentType(CommentContentType contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(Set<String> fileNames) {
        this.fileNames = new HashSet<>(fileNames);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketHistoryComment that = (TicketHistoryComment) o;
        return id.equals(that.id) &&
                action == that.action &&
                contentType == that.contentType &&
                text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, contentType, text);
    }
}
