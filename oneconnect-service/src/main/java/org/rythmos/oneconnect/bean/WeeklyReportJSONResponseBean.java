package org.rythmos.oneconnect.bean;

import java.util.List;

import org.rythmos.oneconnect.entity.RagStatus;

public class WeeklyReportJSONResponseBean {

	private String purpose;

	private String director;

	private String projectEndDate;

	private String scrumMasterFromRythmos;

	private RagStatus ragStatus;

	private String managerLeader;

	private String projectStartDate;

	private String projectName;

	private String productOwner;

	private Long projectId;

	private int numberOfResourses;

	private List<EmployeeProjectMappingBean> resource;

	private List<WRMajorUpdateBean> majorUpdates;

	private List<WRCommentBean> comments;

	private List<WRRiskAndMitigationBean> riskAndMitigations;

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getProjectEndDate() {
		return projectEndDate;
	}

	public void setProjectEndDate(String projectEndDate) {
		this.projectEndDate = projectEndDate;
	}

	public String getScrumMasterFromRythmos() {
		return scrumMasterFromRythmos;
	}

	public void setScrumMasterFromRythmos(String scrumMasterFromRythmos) {
		this.scrumMasterFromRythmos = scrumMasterFromRythmos;
	}

	public RagStatus getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatus ragStatus) {
		this.ragStatus = ragStatus;
	}

	public String getManagerLeader() {
		return managerLeader;
	}

	public void setManagerLeader(String managerLeader) {
		this.managerLeader = managerLeader;
	}

	public String getProjectStartDate() {
		return projectStartDate;
	}

	public void setProjectStartDate(String projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProductOwner() {
		return productOwner;
	}

	public void setProductOwner(String productOwner) {
		this.productOwner = productOwner;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public int getNumberOfResourses() {
		return numberOfResourses;
	}

	public void setNumberOfResourses(int numberOfResourses) {
		this.numberOfResourses = numberOfResourses;
	}

	public List<EmployeeProjectMappingBean> getResource() {
		return resource;
	}

	public void setResource(List<EmployeeProjectMappingBean> resource) {
		this.resource = resource;
	}

	public List<WRMajorUpdateBean> getMajorUpdates() {
		return majorUpdates;
	}

	public void setMajorUpdates(List<WRMajorUpdateBean> majorUpdates) {
		this.majorUpdates = majorUpdates;
	}

	public List<WRCommentBean> getComments() {
		return comments;
	}

	public void setComments(List<WRCommentBean> comments) {
		this.comments = comments;
	}

	public List<WRRiskAndMitigationBean> getRiskAndMitigations() {
		return riskAndMitigations;
	}

	public void setRiskAndMitigations(List<WRRiskAndMitigationBean> riskAndMitigations) {
		this.riskAndMitigations = riskAndMitigations;
	}

	@Override
	public String toString() {
		return "WeeklyReportJSONResponseBean [purpose=" + purpose + ", director=" + director + ", projectEndDate="
				+ projectEndDate + ", scrumMasterFromRythmos=" + scrumMasterFromRythmos + ", ragStatus=" + ragStatus
				+ ", managerLeader=" + managerLeader + ", projectStartDate=" + projectStartDate + ", projectName="
				+ projectName + ", productOwner=" + productOwner + ", projectId=" + projectId + ", numberOfResourses="
				+ numberOfResourses + ", resource=" + resource + ", majorUpdates=" + majorUpdates + ", comments="
				+ comments + ", riskAndMitigations=" + riskAndMitigations + "]";
	}

}
