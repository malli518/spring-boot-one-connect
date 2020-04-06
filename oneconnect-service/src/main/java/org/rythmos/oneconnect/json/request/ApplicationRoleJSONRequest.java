package org.rythmos.oneconnect.json.request;

public class ApplicationRoleJSONRequest {

	private long id;

	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ApplicationRole [id=" + id + ", name=" + name + "]";
	}
}
