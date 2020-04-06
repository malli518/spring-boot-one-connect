package org.rythmos.oneconnect.json.request;

public class EmailJSONRequest {

	private String emailId;
	private String body;
	private String subject;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "EmailJSONRequest [emailId=" + emailId + ", body=" + body + ", subject=" + subject + "]";
	}

}