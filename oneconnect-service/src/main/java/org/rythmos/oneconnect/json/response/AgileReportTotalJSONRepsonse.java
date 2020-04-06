package org.rythmos.oneconnect.json.response;

public class AgileReportTotalJSONRepsonse {

	private float innovation;

	private float partnership;

	private float enthusiasm;

	private float teamwork;

	private float focus;

	private float candor;

	public float getInnovation() {
		return innovation;
	}

	public void setInnovation(float innovation) {
		this.innovation = innovation;
	}

	public float getPartnership() {
		return partnership;
	}

	public void setPartnership(float partnership) {
		this.partnership = partnership;
	}

	public float getEnthusiasm() {
		return enthusiasm;
	}

	public void setEnthusiasm(float enthusiasm) {
		this.enthusiasm = enthusiasm;
	}

	public float getTeamwork() {
		return teamwork;
	}

	public void setTeamwork(float teamwork) {
		this.teamwork = teamwork;
	}

	public float getFocus() {
		return focus;
	}

	public void setFocus(float focus) {
		this.focus = focus;
	}

	public float getCandor() {
		return candor;
	}

	public void setCandor(float candor) {
		this.candor = candor;
	}

	@Override
	public String toString() {
		return "AgileReportTotalJSONRepsonse [innovation=" + innovation + ", partnership=" + partnership
				+ ", enthusiasm=" + enthusiasm + ", teamwork=" + teamwork + ", focus=" + focus + ", candor=" + candor
				+ "]";
	}

}
