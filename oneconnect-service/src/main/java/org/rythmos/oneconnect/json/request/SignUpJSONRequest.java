package org.rythmos.oneconnect.json.request;

import javax.validation.constraints.*;

/**
 * @author Prasanth Kusumaraju
 *
 */
public class SignUpJSONRequest {
	@NotBlank
	@Size(min = 4, max = 40)
	private String name;

	@NotBlank
	@Size(min = 3, max = 15)
	private String username;

	@NotBlank
	@Size(max = 40)
	@Email
	private String email;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;

	private Boolean admin;

	private Boolean executive;

	private Boolean projectRole;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Boolean getExecutive() {
		return executive;
	}

	public void setExecutive(Boolean executive) {
		this.executive = executive;
	}

	public Boolean getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(Boolean projectRole) {
		this.projectRole = projectRole;
	}

}
