package org.rythmos.oneconnect.json.request;

import javax.validation.constraints.NotBlank;

/**
 * @author Prasanth Kusumaraju
 *
 */
public class LoginJSONRequest {
	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
