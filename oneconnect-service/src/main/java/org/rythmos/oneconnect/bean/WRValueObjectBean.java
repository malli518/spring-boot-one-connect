package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class WRValueObjectBean extends AuditBean {
	private Long id;
	private String description;
	private String risk;
	private String mitigation;
	private Long riskAge;
	private Boolean status;

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

	@Override
	public String toString() {
		return "WRValueObjectBean [id=" + id + ", description=" + description + ", risk=" + risk + ", mitigation="
				+ mitigation + ", riskAge=" + riskAge + ", status=" + status + "]";
	}

}
