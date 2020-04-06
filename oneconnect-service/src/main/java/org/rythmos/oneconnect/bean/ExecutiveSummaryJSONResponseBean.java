package org.rythmos.oneconnect.bean;

import java.util.List;

import org.rythmos.oneconnect.entity.RagStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutiveSummaryJSONResponseBean {

	private List<ESummarySummaryBean> summary;

	private String purpose;

	private String director;

	private String projectEndDate;

	private String scrumMasterFromRythmos;

	private int numberOfResourses;

	private List<EmployeeProjectMappingBean> resource;

	private RagStatus ragStatus;

	private List<ESummaryRiskAndMitigationBean> riskAndMitigations;

	private String managerLeader;

	private String projectStartDate;

	private List<ESummaryAccomplishmentBean> accomplishments;

	private String projectName;

	private String productOwner;

	private Long projectId;

	public List<ESummarySummaryBean> getSummary() {
		return summary;
	}

	public void setSummary(List<ESummarySummaryBean> summary) {
		this.summary = summary;
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

	public List<ESummaryRiskAndMitigationBean> getRiskAndMitigations() {
		return riskAndMitigations;
	}

	public void setRiskAndMitigations(List<ESummaryRiskAndMitigationBean> riskAndMitigations) {
		this.riskAndMitigations = riskAndMitigations;
	}

	public RagStatus getRagStatus() {
		return ragStatus;
	}

	public void setRagStatus(RagStatus ragStatus) {
		this.ragStatus = ragStatus;
	}

	public List<ESummaryRiskAndMitigationBean> getRiskAndMitigation() {
		return riskAndMitigations;
	}

	public void setRiskAndMitigation(List<ESummaryRiskAndMitigationBean> riskAndMitigations) {
		this.riskAndMitigations = riskAndMitigations;
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

	public List<ESummaryAccomplishmentBean> getAccomplishments() {
		return accomplishments;
	}

	public void setAccomplishments(List<ESummaryAccomplishmentBean> accomplishments) {
		this.accomplishments = accomplishments;
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
//	public List<ResourceResponseBean> getResources() {
//		return resourceResponses;
//	}
//
//	public void setResources(List<ResourceResponseBean> resourceResponses) {
//		this.resourceResponses = resourceResponses;
//	}

	@Override
	public String toString() {
		return "ExecutiveSummaryJSONResponseBean [summary=" + summary + ", purpose=" + purpose + ", director="
				+ director + ", projectEndDate=" + projectEndDate + ", scrumMasterFromRythmos=" + scrumMasterFromRythmos
				+ ", numberOfResourses=" + numberOfResourses + ", resource=" + resource + ", ragStatus=" + ragStatus
				+ ", riskAndMitigations=" + riskAndMitigations + ", managerLeader=" + managerLeader
				+ ", projectStartDate=" + projectStartDate + ", accomplishments=" + accomplishments + ", projectName="
				+ projectName + ", productOwner=" + productOwner + ", projectId=" + projectId + "]";
	}

}
