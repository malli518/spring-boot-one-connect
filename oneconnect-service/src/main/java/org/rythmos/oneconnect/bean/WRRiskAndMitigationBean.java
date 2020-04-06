package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class WRRiskAndMitigationBean extends AuditBean {

	private Long id;
	private String riskDesc;
	private String mitigationDesc;
	private WRBean weeklyReport;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRiskDesc() {
		return riskDesc;
	}

	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}

	public String getMitigationDesc() {
		return mitigationDesc;
	}

	public void setMitigationDesc(String mitigationDesc) {
		this.mitigationDesc = mitigationDesc;
	}

	public WRBean getWeeklyReport() {
		return weeklyReport;
	}

	public void setWeeklyReport(WRBean weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	@Override
	public String toString() {
		return "WRRiskAndMitigationBean [id=" + id + ", riskDesc=" + riskDesc + ", mitigationDesc=" + mitigationDesc
				+ ", weeklyReport=" + weeklyReport + "]";
	}

}
