package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ProjectDetailBean {

	private Long id;
	private String projectName;
	private Date projectStartDate;
	private Date projectEndDate;
	private String rythmosSM;
	private String director;
	private String productOwner;
	private String manager;
	private String purpose;
	private PortfolioBean portfolio;

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

	public Date getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(Date projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public Date getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(Date projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getRythmosSM() {
		return rythmosSM;
	}

	public void setRythmosSM(String rythmosSM) {
		this.rythmosSM = rythmosSM;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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

	@Override
	public String toString() {
		return "ProjectDetailBean [id=" + id + ", projectName=" + projectName + ", projectStartDate=" + projectStartDate
				+ ", projectEndDate=" + projectEndDate + ", rythmosSM=" + rythmosSM + ", director=" + director
				+ ", productOwner=" + productOwner + ", manager=" + manager + ", purpose=" + purpose + ", portfolio="
				+ portfolio + "]";
	}

}
