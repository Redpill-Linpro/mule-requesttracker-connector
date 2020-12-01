package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

public class TicketLinks extends RequestTrackerObject {
    private String HasMember; // fsck.com-rt://your.server.com/ticket/<another-id>
    private String ReferredToBy; // fsck.com-rt://your.server.com/ticket/<another-id>
    private String DependedOnBy; // fsck.com-rt://your.server.com/ticket/<another-id>
    private String MemberOf; // fsck.com-rt://your.server.com/ticket/<another-id>
    private String RefersTo; // fsck.com-rt://your.server.com/ticket/<another-id>
    private String DependsOn; // fsck.com-rt://your.server.com/ticket/<another-id>

    public String getHasMember() {
        return HasMember;
    }

    public void setHasMember(String hasMember) {
        HasMember = hasMember;
    }

    public String getReferredToBy() {
        return ReferredToBy;
    }

    public void setReferredToBy(String referredToBy) {
        ReferredToBy = referredToBy;
    }

    public String getDependedOnBy() {
        return DependedOnBy;
    }

    public void setDependedOnBy(String dependedOnBy) {
        DependedOnBy = dependedOnBy;
    }

    public String getMemberOf() {
        return MemberOf;
    }

    public void setMemberOf(String memberOf) {
        MemberOf = memberOf;
    }

    public String getRefersTo() {
        return RefersTo;
    }

    public void setRefersTo(String refersTo) {
        RefersTo = refersTo;
    }

    public String getDependsOn() {
        return DependsOn;
    }

    public void setDependsOn(String dependsOn) {
        DependsOn = dependsOn;
    }
}
