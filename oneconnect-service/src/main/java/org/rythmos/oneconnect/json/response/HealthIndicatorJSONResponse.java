package org.rythmos.oneconnect.json.response;

public class HealthIndicatorJSONResponse {

	private String month;

	private HealthIndicatorPointsJSONResponse healthIndicatorPoints;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public HealthIndicatorPointsJSONResponse getHealthIndicatorPoints() {
		return healthIndicatorPoints;
	}

	public void setHealthIndicatorPoints(HealthIndicatorPointsJSONResponse healthIndicatorPoints) {
		this.healthIndicatorPoints = healthIndicatorPoints;
	}

	@Override
	public String toString() {
		return "HealthIndicatorJSONResponse [month=" + month + ", healthIndicatorPoints=" + healthIndicatorPoints + "]";
	}

}
