package org.rythmos.oneconnect.json.request;

public class StateJSONRequest {
	private String name;

	private String id;

	private String countryId;

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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "StateJSONRequest [name = " + name + ", id = " + id + ", countryId = " + countryId + "]";
	}
}
