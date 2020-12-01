package org.mule.extension.requesttracker.api.models.request;

import org.mule.extension.requesttracker.internal.annotations.RTName;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import java.util.Objects;

public class UpdateUser {

    @Parameter
    @Optional
    @RTName("Name")
    private String userName;
    @Parameter
    @Optional
    @DisplayName("Email Address")
    private String emailAddress;
    @Parameter
    @Optional
    @DisplayName("Real Name")
    private String realName;
    @Parameter
    @Optional
    private String organization;
    @Parameter
    @Optional
    private Boolean privileged;
    @Parameter
    @Optional
    private Boolean disabled;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getPrivileged() {
        return privileged;
    }

    public void setPrivileged(Boolean privileged) {
        this.privileged = privileged;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateUser that = (UpdateUser) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(realName, that.realName) &&
                Objects.equals(organization, that.organization) &&
                Objects.equals(privileged, that.privileged) &&
                Objects.equals(disabled, that.disabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, emailAddress, realName, organization, privileged, disabled);
    }
}
