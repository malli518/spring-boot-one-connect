package org.rythmos.oneconnect.json.request;

import java.util.List;

public class EmployeeJSONRequest {

	private String id;
	private String name;
	private String empId;
	private String emailId;
	private String joiningDate;
	private String relievingDate;

	private List<EmployeeProjectJSONRequest> projects;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getRelievingDate() {
		return relievingDate;
	}

	public void setRelievingDate(String relievingDate) {
		this.relievingDate = relievingDate;
	}

	public List<EmployeeProjectJSONRequest> getProjects() {
		return projects;
	}

	public void setProjects(List<EmployeeProjectJSONRequest> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "EmployeeJSONRequest [id=" + id + ", name=" + name + ", empId=" + empId + ", emailId=" + emailId
				+ ", joiningDate=" + joiningDate + ", relievingDate=" + relievingDate + ", projects=" + projects + "]";
	}

}