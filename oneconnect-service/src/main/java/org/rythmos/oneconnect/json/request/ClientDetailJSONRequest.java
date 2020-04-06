package org.rythmos.oneconnect.json.request;

public class ClientDetailJSONRequest {
	private Long id;
	private String clientId;
	private String clientName;
	private String clientDescription;
	private LocationJSONRequest location;
	private String status;

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

	public LocationJSONRequest getLocation() {
		return location;
	}

	public void setLocation(LocationJSONRequest location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ClientDetailJSONRequest [clientId=" + clientId + ", clientName=" + clientName + ", clientDescription="
				+ clientDescription + ", location=" + location + ", status=" + status + "]";
	}

}
