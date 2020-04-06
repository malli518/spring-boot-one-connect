package org.rythmos.oneconnect.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.rythmos.oneconnect.audit.Auditable;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "employee_id", "project_detail_id", "assignedDate", "status" }) })

public class EmployeeProjectMapping extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column
	private String utilization;

	@Column
	private String billingStatus;

	@Column
	private Date assignedDate;

	@Column
	private Date releaseDate;

	@Column
	private String status;

	@ManyToOne
	private Employee employee;

	@ManyToOne
	private ProjectDetail projectDetail;

	@ManyToOne
	private ProjectRole projectRole;

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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ProjectDetail getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(ProjectDetail projectDetail) {
		this.projectDetail = projectDetail;
	}

	public ProjectRole getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(ProjectRole projectRole) {
		this.projectRole = projectRole;
	}

	@Override
	public String toString() {
		return "EmployeeProjectMapping [id=" + id + ", utilization=" + utilization + ", billingStatus=" + billingStatus
				+ ", assignedDate=" + assignedDate + ", releaseDate=" + releaseDate + ", status=" + status
				+ ", employee=" + employee + ", projectDetail=" + projectDetail + ", projectRole=" + projectRole + "]";
	}

}
