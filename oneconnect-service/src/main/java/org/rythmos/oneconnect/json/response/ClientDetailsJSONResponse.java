package org.rythmos.oneconnect.json.response;

public class ClientDetailsJSONResponse {

	private Long id;

	private String clientId;

	private String clientName;

	private String clientDescription;

	private String status;

	private LocationJSONResponse location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientDescription() {
		return clientDescription;
	}

	public void setClientDescription(String clientDescription) {
		this.clientDescription = clientDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocationJSONResponse getLocation() {
		return location;
	}

	public void setLocation(LocationJSONResponse location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "ClientDetailsJSONResponse [id=" + id + ", clientId=" + clientId + ", clientName=" + clientName
				+ ", clientDescription=" + clientDescription + ", status=" + status + ", location=" + location + "]";
	}
}