package org.rythmos.oneconnect.json.response;

public class AgileIndicatorJSONResponse {
	
	private String month;

	private AgileIndicatorPointsJSONResponse agilePoints;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public AgileIndicatorPointsJSONResponse getAgilePoints() {
		return agilePoints;
	}

	public void setAgilePoints(AgileIndicatorPointsJSONResponse agilePoints) {
		this.agilePoints = agilePoints;
	}

	@Override
	public String toString() {
		return "AgileIndicatorJSONResponse [month=" + month + ", agilePoints=" + agilePoints + "]";
	}
	
	
}
