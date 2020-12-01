package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

public class Queue extends RequestTrackerObject {
    private String Name;
    private String Description;
    private String CorrespondAddress;
    private String CommentAddress;
    private String InitialPriority;
    private String FinalPriority;
    private String DefaultDueIn;
    private Boolean Disabled;
    private Boolean SLADisabled;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCorrespondAddress() {
        return CorrespondAddress;
    }

    public void setCorrespondAddress(String correspondAddress) {
        CorrespondAddress = correspondAddress;
    }

    public String getCommentAddress() {
        return CommentAddress;
    }

    public void setCommentAddress(String commentAddress) {
        CommentAddress = commentAddress;
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

    public String getDefaultDueIn() {
        return DefaultDueIn;
    }

    public void setDefaultDueIn(String defaultDueIn) {
        DefaultDueIn = defaultDueIn;
    }

    public Boolean getDisabled() {
        return Disabled;
    }

    public void setDisabled(Boolean disabled) {
        Disabled = disabled;
    }

    public Boolean getSLADisabled() {
        return SLADisabled;
    }

    public void setSLADisabled(Boolean SLADisabled) {
        this.SLADisabled = SLADisabled;
    }
}
