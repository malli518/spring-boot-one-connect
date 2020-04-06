package org.rythmos.oneconnect.json.response;

/**
 * @author Prasanth Kusumaraju
 *
 */
public class EmployeeProjectJSONResponse {

	private Long id;

	private String projectName;

	private Long projectId;

	private ProjectRoleJSONResponse projectRole;

	private String utilization;

	private String billingStatus;

	private ClientBasicInfoJSONResponse client;

	private PortfolioBasicInfoJSONResponse portfolio;

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

	public ProjectRoleJSONResponse getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(ProjectRoleJSONResponse projectRole) {
		this.projectRole = projectRole;
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

	public ClientBasicInfoJSONResponse getClient() {
		return client;
	}

	public void setClient(ClientBasicInfoJSONResponse client) {
		this.client = client;
	}

	public PortfolioBasicInfoJSONResponse getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBasicInfoJSONResponse portfolio) {
		this.portfolio = portfolio;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String toString() {
		return "EmployeeProjectJSONResponse [id=" + id + ", projectName=" + projectName + ", projectId=" + projectId
				+ ", projectRole=" + projectRole + ", utilization=" + utilization + ", billingStatus=" + billingStatus
				+ ", client=" + client + ", portfolio=" + portfolio + "]";
	}

}
