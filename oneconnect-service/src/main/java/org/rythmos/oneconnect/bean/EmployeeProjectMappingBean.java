package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class EmployeeProjectMappingBean extends AuditBean {

	private long id;

	private String utilization;

	private String billingStatus;

	private Date assignedDate;

	private Date releaseDate;

	private String status;

	private EmployeeBean employee;

	private ProjectDetailBean projectDetail;

	private ProjectRoleBean projectRole;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public EmployeeBean getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeBean employee) {
		this.employee = employee;
	}

	public ProjectDetailBean getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetailBean projectDetail) {
		this.projectDetail = projectDetail;
	}

	public ProjectRoleBean getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(ProjectRoleBean projectRole) {
		this.projectRole = projectRole;
	}

	@Override
	public String toString() {
		return "EmployeeProjectMappingBean [id=" + id + ", utilization=" + utilization + ", billingStatus="
				+ billingStatus + ", assignedDate=" + assignedDate + ", releaseDate=" + releaseDate + ", status="
				+ status + ", employee=" + employee + ", projectDetail=" + projectDetail + ", projectRole="
				+ projectRole + "]";
	}

}
