package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class ClientDetailBean {

	private Long id;

	private String clientId;

	private String clientDescription;

	private String clientName;

	private String status;

	private LocationBean location;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getClientDescription() {
		return clientDescription;
	}

	public void setClientDescription(String clientDescription) {
		this.clientDescription = clientDescription;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public LocationBean getLocation() {
		return location;
	}

	public void setLocation(LocationBean location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "ClientDetailBean [id=" + id + ", clientId=" + clientId + ", clientDescription=" + clientDescription
				+ ", clientName=" + clientName + ", status=" + status + ", location=" + location + "]";
	}

}
