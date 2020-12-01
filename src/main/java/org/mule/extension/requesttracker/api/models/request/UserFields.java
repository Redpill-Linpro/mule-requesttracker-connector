package org.mule.extension.requesttracker.api.models.request;

import org.mule.extension.requesttracker.internal.annotations.RTName;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import java.util.Objects;

public class UserFields implements Fields {

    @Parameter
    @Optional
    @RTName("Name")
    private boolean userName;
    @Parameter
    @Optional
    private boolean emailAddress;
    @Parameter
    @Optional
    private boolean realName;
    @Parameter
    @Optional
    private boolean gecos;
    @Parameter
    @Optional
    private boolean comments;
    @Parameter
    @Optional
    private boolean organization;
    @Parameter
    @Optional
    private boolean privileged;
    @Parameter
    @Optional
    private boolean disabled;

    public boolean isUserName() {
        return userName;
    }

    public void setUserName(boolean userName) {
        this.userName = userName;
    }

    public boolean isEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(boolean emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isRealName() {
        return realName;
    }

    public void setRealName(boolean realName) {
        this.realName = realName;
    }

    public boolean isGecos() {
        return gecos;
    }

    public void setGecos(boolean gecos) {
        this.gecos = gecos;
    }

    public boolean isComments() {
        return comments;
    }

    public void setComments(boolean comments) {
        this.comments = comments;
    }

    public boolean isOrganization() {
        return organization;
    }

    public void setOrganization(boolean organization) {
        this.organization = organization;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFields that = (UserFields) o;
        return userName == that.userName &&
                emailAddress == that.emailAddress &&
                realName == that.realName &&
                gecos == that.gecos &&
                comments == that.comments &&
                organization == that.organization &&
                privileged == that.privileged &&
                disabled == that.disabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, emailAddress, realName, gecos, comments, organization, privileged, disabled);
    }
}
