package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.PortfolioBean;

public class ProjectDetailJSONRequest {

	private Long id;

	private String projectName;

	private String scrumMaster;

	private String managerLeader;

	private String purpose;

	private PortfolioBean portfolio;

	private String productOwner;

	private String director;

	private String startDate;

	private String endDate;

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

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "ProjectDetailJSONRequest [id=" + id + ", projectName=" + projectName + ", scrumMaster=" + scrumMaster
				+ ", managerLeader=" + managerLeader + ", purpose=" + purpose + ", portfolio=" + portfolio
				+ ", productOwner=" + productOwner + ", director=" + director + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

}
