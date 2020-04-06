package org.rythmos.oneconnect.json.response;

public class WRRiskAndMitigationJSONResponse {

	private Long id;
	private String risk;
	private String mitigation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "WRRiskAndMitigationJSONResponse [id=" + id + ", risk=" + risk + ", mitigation=" + mitigation + "]";
	}

}
