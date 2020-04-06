package org.rythmos.oneconnect.json.response;

public class ProjectDetailJSONResponse {

	private Long portfolioId;

	private String portfolioName;

	private String scrumMaster;

	private String managerLeader;

	private Long clientId;

	private String clientName;

	private String purpose;

	private String endDate;

	private String director;

	private Long id;

	private String projectName;

	private String productOwner;

	private String startDate;

	public Long getPortfolioId() {
		return portfolioId;
	}

	public void setPortfolioId(Long portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getScrumMaster() {
		return scrumMaster;
	}

	public void setScrumMaster(String scrumMaster) {
		this.scrumMaster = scrumMaster;
	}

	public String getManagerLeader() {
		return managerLeader;
	}

	public void setManagerLeader(String managerLeader) {
		this.managerLeader = managerLeader;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPortfolioName() {
		return portfolioName;
	}

	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "ProjectDetailJSONResponse [portfolioId=" + portfolioId + ", portfolioName=" + portfolioName
				+ ", scrumMaster=" + scrumMaster + ", managerLeader=" + managerLeader + ", clientId=" + clientId
				+ ", clientName=" + clientName + ", purpose=" + purpose + ", endDate=" + endDate + ", director="
				+ director + ", id=" + id + ", projectName=" + projectName + ", productOwner=" + productOwner
				+ ", startDate=" + startDate + "]";
	}

}
