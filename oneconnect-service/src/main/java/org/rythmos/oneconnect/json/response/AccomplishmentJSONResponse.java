package org.rythmos.oneconnect.json.response;

public class AccomplishmentJSONResponse {

	private Long id;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AccomplishmentJSONResponse [id=" + id + ", description=" + description + "]";
	}

}
