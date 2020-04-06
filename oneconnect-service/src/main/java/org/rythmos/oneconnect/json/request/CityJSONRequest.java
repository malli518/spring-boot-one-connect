package org.rythmos.oneconnect.json.request;

public class CityJSONRequest {
	private String stateId;

	private String name;

	private String id;

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ClassPojo [stateId = " + stateId + ", name = " + name + ", id = " + id + "]";
	}
}
