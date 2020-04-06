package org.rythmos.oneconnect.bean;

public class SenderBean {
	private String name;

	private String emailId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "SenderBean [name=" + name + ", emailId=" + emailId + "]";
	}

}
