package org.rythmos.oneconnect.json.request;

public class UpdatePwdJSONRequest {
	private Long id;
	private String currentPassword;
	private String newPassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public String toString() {
		return "UpdatePwdJSONRequest [id=" + id + ", currentPassword=" + currentPassword + ", newPassword="
				+ newPassword + "]";
	}

}
