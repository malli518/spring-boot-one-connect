package org.rythmos.oneconnect.json.response;

public class ResourceJSONResponse {
	private String empId;

	private String name;

	private String billingStatus;

	private String percentageOfUtilization;

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

	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public String getPercentageOfUtilization() {
		return percentageOfUtilization;
	}

	public void setPercentageOfUtilization(String percentageOfUtilization) {
		this.percentageOfUtilization = percentageOfUtilization;
	}

	@Override
	public String toString() {
		return "ClassPojo [empId = " + empId + ", name = " + name + ", billingStatus = " + billingStatus
				+ ", percentageOfUtilization = " + percentageOfUtilization + "]";
	}
}
