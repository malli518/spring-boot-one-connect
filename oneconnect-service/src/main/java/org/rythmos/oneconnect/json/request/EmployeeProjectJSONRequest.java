package org.rythmos.oneconnect.json.request;

public class EmployeeProjectJSONRequest {

	private Long id;
	private Long projectId;
	private String name;
	private String utilization;
	private String billingStatus;
	private IdNameJSONRequest projectRole;
	private IdNameJSONRequest client;
	private IdNameJSONRequest portfolio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUtilization() {
		return utilization;
	}

	public void setUtilization(String utilization) {
		this.utilization = utilization;
	}

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public IdNameJSONRequest getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(IdNameJSONRequest projectRole) {
		this.projectRole = projectRole;
	}

	public IdNameJSONRequest getClient() {
		return client;
	}

	public void setClient(IdNameJSONRequest client) {
		this.client = client;
	}

	public IdNameJSONRequest getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(IdNameJSONRequest portfolio) {
		this.portfolio = portfolio;
	}

	@Override
	public String toString() {
		return "EmployeeProjectJSONRequest [id=" + id + ", projectId=" + projectId + ", name=" + name + ", utilization="
				+ utilization + ", billingStatus=" + billingStatus + ", projectRole=" + projectRole + ", client="
				+ client + ", portfolio=" + portfolio + "]";
	}
}
