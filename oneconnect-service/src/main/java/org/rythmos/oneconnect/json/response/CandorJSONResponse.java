package org.rythmos.oneconnect.json.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CandorJSONResponse {
	private Long count;

	private List<AssessmentDataJSONResponse> assessmentDataJSONResponse = new ArrayList<AssessmentDataJSONResponse>();

//	@Autowired
//	private List<AssessmentDataJSONRequest> assessmentDataJSONResponse;
//null assessment data is inserted to the list while using autowired anotation

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<AssessmentDataJSONResponse> getAssessmentData() {
		return assessmentDataJSONResponse;
	}

	public void setAssessmentData(List<AssessmentDataJSONResponse> assessmentDataJSONResponse) {
		this.assessmentDataJSONResponse = assessmentDataJSONResponse;
	}

	@Override
	public String toString() {
		return "CandorJSONResponse [count=" + count + ", assessmentDataJSONResponse=" + assessmentDataJSONResponse
				+ "]";
	}

}
