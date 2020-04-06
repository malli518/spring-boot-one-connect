package org.rythmos.oneconnect.json.response;

import java.util.List;

public class ClientHealthReportJSONResponse {
	private List<FeedbackJSONResponse> feedbackData;

	private List<HealthIndicatorJSONResponse> healthIndicatorData;

	public List<FeedbackJSONResponse> getFeedbackData() {
		return feedbackData;
	}

	public void setFeedbackData(List<FeedbackJSONResponse> feedbackData) {
		this.feedbackData = feedbackData;
	}

	public List<HealthIndicatorJSONResponse> getHealthIndicatorData() {
		return healthIndicatorData;
	}

	public void setHealthIndicatorData(List<HealthIndicatorJSONResponse> healthIndicatorData) {
		this.healthIndicatorData = healthIndicatorData;
	}

	@Override
	public String toString() {
		return "ClientHealthReportJSONResponse [feedbackData=" + feedbackData + ", healthIndicatorData="
				+ healthIndicatorData + "]";
	}

}
