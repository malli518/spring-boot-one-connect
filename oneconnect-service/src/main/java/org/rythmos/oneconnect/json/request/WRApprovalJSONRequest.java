package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;

public class WRApprovalJSONRequest {

	private ClientDetailBean client;

	private PortfolioBean portfolio;

	private ProjectDetailBean project;

	private String startDate;

	private String endDate;

	private Long projectQuality;

	private Long codeQuality;

	public ClientDetailBean getClient() {
		return client;
	}

	public void setClient(ClientDetailBean client) {
		this.client = client;
	}

	public PortfolioBean getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioBean portfolio) {
		this.portfolio = portfolio;
	}

	public ProjectDetailBean getProject() {
		return project;
	}

	public void setProject(ProjectDetailBean project) {
		this.project = project;
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

	public Long getProjectQuality() {
		return projectQuality;
	}

	public void setProjectQuality(Long projectQuality) {
		this.projectQuality = projectQuality;
	}

	public Long getCodeQuality() {
		return codeQuality;
	}

	public void setCodeQuality(Long codeQuality) {
		this.codeQuality = codeQuality;
	}

	@Override
	public String toString() {
		return "WRApprovalJSONRequest [client=" + client + ", portfolio=" + portfolio + ", project=" + project
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", projectQuality=" + projectQuality
				+ ", codeQuality=" + codeQuality + "]";
	}

}
