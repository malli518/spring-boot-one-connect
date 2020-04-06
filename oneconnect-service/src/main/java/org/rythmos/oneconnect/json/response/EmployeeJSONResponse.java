package org.rythmos.oneconnect.json.response;

import java.util.List;

/**
 * @author Prasanth Kusumaraju
 *
 */
public class EmployeeJSONResponse {
	private Long id;

	private String empId;

	private String name;
	private String emailId;

	private List<EmployeeProjectJSONResponse> projects;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public List<EmployeeProjectJSONResponse> getProjects() {
		return projects;
	}

	public void setProjects(List<EmployeeProjectJSONResponse> projects) {
		this.projects = projects;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "EmployeeJSONResponse [id=" + id + ", empId=" + empId + ", name=" + name + ", emailId=" + emailId
				+ ", projects=" + projects + "]";
	}

}