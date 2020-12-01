package org.mule.extension.requesttracker.api.models.response;

import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;

public class User extends RequestTrackerObject {
	private String Name;
	private String Password;
	private String EmailAddress; 
	private String RealName;
	private String Organization;
	private String Gecos;
	private String Comments;
	private Boolean Privileged;
	private Boolean Disabled;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}
	public String getRealName() {
		return RealName;
	}
	public void setRealName(String realName) {
		RealName = realName;
	}

	public String getGecos() {
		return Gecos;
	}

	public void setGecos(String gecos) {
		Gecos = gecos;
	}

	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		this.Comments = comments;
	}

	public String getOrganization() {
		return Organization;
	}
	public void setOrganization(String organization) {
		Organization = organization;
	}
	public Boolean getPrivileged() {
		return Privileged;
	}
	public void setPrivileged(Boolean privileged) {
		Privileged = privileged;
	}
	public Boolean getDisabled() {
		return Disabled;
	}
	public void setDisabled(Boolean disabled) {
		Disabled = disabled;
	}
	
	
}
