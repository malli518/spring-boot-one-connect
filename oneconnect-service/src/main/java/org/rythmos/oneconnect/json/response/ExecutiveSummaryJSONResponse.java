package org.rythmos.oneconnect.json.response;

import java.util.List;

import org.rythmos.oneconnect.entity.RagStatus;

public class ExecutiveSummaryJSONResponse {

	private List<SummaryJSONResponse> summary;

	private String purpose;

	private String director;

	private String projectEndDate;

	private String scrumMasterFromRythmos;

	private int numberOfResources;

	private List<ResourceJSONResponse> resources;

	private RagStatus ragStatus;

	private List<RiskAndMitigationJSONResponse> risksAndMitigations;

	private String managerLeader;

	private String projectStartDate;

	private List<AccomplishmentJSONResponse> accomplishments;

	private String projectName;

	private String productOwner;

	private Long projectId;

	public List<ResourceJSONResponse> getResources() {
		return resources;
	}

	public void setResources(List<ResourceJSONResponse> resources) {
		this.resources = resources;
	}

	public List<SummaryJSONResponse> getSummary() {
		return summary;
	}

	public void setSummary(List<SummaryJSONResponse> summary) {
		this.summary = summary;
	}

	public List<RiskAndMitigationJSONResponse> getRisksAndMitigations() {
		return risksAndMitigations;
	}

	public void setRisksAndMitigations(List<RiskAndMitigationJSONResponse> risksAndMitigations) {
		this.risksAndMitigations = risksAndMitigations;
	}

	public List<AccomplishmentJSONResponse> getAccomplishments() {
		return accomplishments;
	}

	public void setAccomplishments(List<AccomplishmentJSONResponse> accomplishments) {
		this.accomplishments = accomplishments;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

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

	public int getNumberOfResources() {
		return numberOfResources;
	}

	public void setNumberOfResources(int numberOfResources) {
		this.numberOfResources = numberOfResources;
	}

	@Override
	public String toString() {
		return "ExecutiveSummaryJSONResponse [summary=" + summary + ", purpose=" + purpose + ", director=" + director
				+ ", projectEndDate=" + projectEndDate + ", scrumMasterFromRythmos=" + scrumMasterFromRythmos
				+ ", numberOfResourses=" + numberOfResources + ", resources=" + resources + ", ragStatus=" + ragStatus
				+ ", risksAndMitigations=" + risksAndMitigations + ", managerLeader=" + managerLeader
				+ ", projectStartDate=" + projectStartDate + ", accomplishments=" + accomplishments + ", projectName="
				+ projectName + ", productOwner=" + productOwner + ", projectId=" + projectId + "]";
	}

}