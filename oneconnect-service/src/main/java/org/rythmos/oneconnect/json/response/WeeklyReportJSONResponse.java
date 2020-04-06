package org.rythmos.oneconnect.json.response;

import java.util.List;

import org.rythmos.oneconnect.entity.RagStatus;

public class WeeklyReportJSONResponse {

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

	private int numberOfResources;

	private List<ResourceJSONResponse> resources;

	private List<WRMajorUpdateJSONResponse> majorUpdates;

	private List<WRCommentJSONResponse> comments;

	private List<WRRiskAndMitigationJSONResponse> risksAndMitigations;

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

	public int getNumberOfResources() {
		return numberOfResources;
	}

	public void setNumberOfResources(int numberOfResources) {
		this.numberOfResources = numberOfResources;
	}

	public List<ResourceJSONResponse> getResources() {
		return resources;
	}

	public void setResources(List<ResourceJSONResponse> resources) {
		this.resources = resources;
	}

	public List<WRMajorUpdateJSONResponse> getMajorUpdates() {
		return majorUpdates;
	}

	public void setMajorUpdates(List<WRMajorUpdateJSONResponse> majorUpdates) {
		this.majorUpdates = majorUpdates;
	}

	public List<WRCommentJSONResponse> getComments() {
		return comments;
	}

	public void setComments(List<WRCommentJSONResponse> comments) {
		this.comments = comments;
	}

	public List<WRRiskAndMitigationJSONResponse> getRisksAndMitigations() {
		return risksAndMitigations;
	}

	public void setRisksAndMitigations(List<WRRiskAndMitigationJSONResponse> risksAndMitigations) {
		this.risksAndMitigations = risksAndMitigations;
	}

	@Override
	public String toString() {
		return "WeeklyReportJSONResponse [purpose=" + purpose + ", director=" + director + ", projectEndDate="
				+ projectEndDate + ", scrumMasterFromRythmos=" + scrumMasterFromRythmos + ", ragStatus=" + ragStatus
				+ ", managerLeader=" + managerLeader + ", projectStartDate=" + projectStartDate + ", projectName="
				+ projectName + ", productOwner=" + productOwner + ", projectId=" + projectId + ", numberOfResources="
				+ numberOfResources + ", resources=" + resources + ", majorUpdates=" + majorUpdates + ", comments="
				+ comments + ", risksAndMitigations=" + risksAndMitigations + "]";
	}

}
