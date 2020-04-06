package org.rythmos.oneconnect.json.response;

public class CityJSONResponse {
	private Long stateId;

	private String name;

	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ClassPojo [stateId = " + stateId + ", name = " + name + ", id = " + id + "]";
	}
}
