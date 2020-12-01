package org.mule.extension.requesttracker.api.models.request;

import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import java.util.Objects;

public class UpdateTicketLinks {

    @Parameter
    @Optional
    private String hasMember;
    @Parameter
    @Optional
    private String referredToBy;
    @Parameter
    @Optional
    private String dependedOnBy;
    @Parameter
    @Optional
    private String memberOf;
    @Parameter
    @Optional
    private String refersTo;
    @Parameter
    @Optional
    private String dependsOn;

    public String getHasMember() {
        return hasMember;
    }

    public void setHasMember(String hasMember) {
        this.hasMember = hasMember;
    }

    public String getReferredToBy() {
        return referredToBy;
    }

    public void setReferredToBy(String referredToBy) {
        this.referredToBy = referredToBy;
    }

    public String getDependedOnBy() {
        return dependedOnBy;
    }

    public void setDependedOnBy(String dependedOnBy) {
        this.dependedOnBy = dependedOnBy;
    }

    public String getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(String memberOf) {
        this.memberOf = memberOf;
    }

    public String getRefersTo() {
        return refersTo;
    }

    public void setRefersTo(String refersTo) {
        this.refersTo = refersTo;
    }

    public String getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String dependsOn) {
        this.dependsOn = dependsOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateTicketLinks that = (UpdateTicketLinks) o;
        return Objects.equals(hasMember, that.hasMember) &&
                Objects.equals(referredToBy, that.referredToBy) &&
                Objects.equals(dependedOnBy, that.dependedOnBy) &&
                Objects.equals(memberOf, that.memberOf) &&
                Objects.equals(refersTo, that.refersTo) &&
                Objects.equals(dependsOn, that.dependsOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasMember, referredToBy, dependedOnBy, memberOf, refersTo, dependsOn);
    }
}
