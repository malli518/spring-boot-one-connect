package org.rythmos.oneconnect.json.response;

public class AgileIndicatorPointsJSONResponse {
	
	private float partnership;

	private float teamwork;

	private float enthusiasm;

	private float candor;

	private float focus;
	
	private float innovation;

	public float getPartnership() {
		return partnership;
	}

	public void setPartnership(float partnership) {
		this.partnership = partnership;
	}

	public float getTeamwork() {
		return teamwork;
	}

	public void setTeamwork(float teamwork) {
		this.teamwork = teamwork;
	}

	public float getEnthusiasm() {
		return enthusiasm;
	}

	public void setEnthusiasm(float enthusiasm) {
		this.enthusiasm = enthusiasm;
	}

	public float getCandor() {
		return candor;
	}

	public void setCandor(float candor) {
		this.candor = candor;
	}

	public float getFocus() {
		return focus;
	}

	public void setFocus(float focus) {
		this.focus = focus;
	}

	public float getInnovation() {
		return innovation;
	}

	public void setInnovation(float innovation) {
		this.innovation = innovation;
	}

	@Override
	public String toString() {
		return "AgileIndicatorPointsJSONResponse [partnership=" + partnership + ", teamwork=" + teamwork
				+ ", enthusiasm=" + enthusiasm + ", candor=" + candor + ", focus=" + focus + ", innovation="
				+ innovation + "]";
	}
	
	
}
