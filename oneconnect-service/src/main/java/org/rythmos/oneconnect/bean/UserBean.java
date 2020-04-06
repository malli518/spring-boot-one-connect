package org.rythmos.oneconnect.bean;

import java.util.Arrays;

public class UserBean {

	private Long id;
	private String name;
	private String username;
	private String email;
	private String password;
	private byte[] data;
	private String fileName;
	private String fileType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "UserBean [id=" + id + ", name=" + name + ", username=" + username + ", email=" + email + ", password="
				+ password + ", data=" + Arrays.toString(data) + ", fileName=" + fileName + ", fileType=" + fileType
				+ "]";
	}

}
