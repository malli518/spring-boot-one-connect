package org.rythmos.oneconnect.json.request;

import org.rythmos.oneconnect.bean.ClientDetailBean;
import org.rythmos.oneconnect.bean.PortfolioBean;
import org.rythmos.oneconnect.bean.ProjectDetailBean;

public class ReportDateJSONRequest {
	
	private ClientDetailBean client;

	private PortfolioBean portfolio;

	private ProjectDetailBean project;
	
	private String reportType;

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

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	@Override
	public String toString() {
		return "DateReportJSONRequest [client=" + client + ", portfolio=" + portfolio + ", project=" + project
				+ ", reportType=" + reportType + "]";
	}
	
	
}
