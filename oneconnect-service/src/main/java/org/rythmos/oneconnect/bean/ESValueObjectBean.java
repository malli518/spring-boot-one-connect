package org.rythmos.oneconnect.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ESValueObjectBean {

	private Long id;
	private String description;
	private String risk;
	private String mitigation;
	private Long riskAge;
	private Boolean status;
	private Date createdDate;
	private Date closedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public String getMitigation() {
		return mitigation;
	}

	public void setMitigation(String mitigation) {
		this.mitigation = mitigation;
	}

	public Long getRiskAge() {
		return riskAge;
	}

	public void setRiskAge(Long riskAge) {
		this.riskAge = riskAge;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	@Override
	public String toString() {
		return "ESValueObjectBean [id=" + id + ", description=" + description + ", risk=" + risk + ", mitigation="
				+ mitigation + ", riskAge=" + riskAge + ", status=" + status + ", createdDate=" + createdDate
				+ ", closedDate=" + closedDate + "]";
	}

}
