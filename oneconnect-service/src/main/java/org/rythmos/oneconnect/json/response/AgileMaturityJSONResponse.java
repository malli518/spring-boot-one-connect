package org.rythmos.oneconnect.json.response;

import java.util.List;

public class AgileMaturityJSONResponse {

	private AgileReportAverageJSONRepsonse average;

	private AgileReportTotalJSONRepsonse total;

	private List<AssessmentDataJSONRepsonse> assessmentData;
	/*New*/
	private List<AgileIndicatorJSONResponse> agileData;

	public AgileReportAverageJSONRepsonse getAverage() {
		return average;
	}

	public void setAverage(AgileReportAverageJSONRepsonse average) {
		this.average = average;
	}

	public AgileReportTotalJSONRepsonse getTotal() {
		return total;
	}

	public void setTotal(AgileReportTotalJSONRepsonse total) {
		this.total = total;
	}

	public List<AssessmentDataJSONRepsonse> getAssessmentData() {
		return assessmentData;
	}

	public void setAssessmentData(List<AssessmentDataJSONRepsonse> assessmentData) {
		this.assessmentData = assessmentData;
	}

	

	public List<AgileIndicatorJSONResponse> getAgileData() {
		return agileData;
	}

	public void setAgileData(List<AgileIndicatorJSONResponse> agileData) {
		this.agileData = agileData;
	}

	@Override
	public String toString() {
		return "AgileMaturityJSONResponse [average=" + average + ", total=" + total + ", assessmentData="
				+ assessmentData + ", agileData=" + agileData + "]";
	}

	

}
