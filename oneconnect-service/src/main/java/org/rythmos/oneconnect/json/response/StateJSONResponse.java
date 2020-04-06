package org.rythmos.oneconnect.json.response;

public class StateJSONResponse {
	private String name;

	private Long id;

	private Long countryId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Override
	public String toString() {
		return "ClassPojo [name = " + name + ", id = " + id + ", countryId = " + countryId + "]";
	}
}
