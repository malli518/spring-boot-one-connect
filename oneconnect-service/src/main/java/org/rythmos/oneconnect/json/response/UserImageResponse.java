package org.rythmos.oneconnect.json.response;

public class UserImageResponse {
	private Long id;
	private String data;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "UserImageResponse [id=" + id + ", data=" + data + "]";
	}

}
