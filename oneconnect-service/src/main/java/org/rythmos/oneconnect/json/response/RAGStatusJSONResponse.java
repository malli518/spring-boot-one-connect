package org.rythmos.oneconnect.json.response;

import java.util.List;

public class RAGStatusJSONResponse {

	private List<ProjectEsummaryJSONResponse> green;
	private List<ProjectEsummaryJSONResponse> amber;
	private List<ProjectEsummaryJSONResponse> red;

	public List<ProjectEsummaryJSONResponse> getGreen() {
		return green;
	}

	public void setGreen(List<ProjectEsummaryJSONResponse> green) {
		this.green = green;
	}

	public List<ProjectEsummaryJSONResponse> getAmber() {
		return amber;
	}

	public void setAmber(List<ProjectEsummaryJSONResponse> amber) {
		this.amber = amber;
	}

	public List<ProjectEsummaryJSONResponse> getRed() {
		return red;
	}

	public void setRed(List<ProjectEsummaryJSONResponse> red) {
		this.red = red;
	}

	@Override
	public String toString() {
		return "RAGStatusJSONResponse [green=" + green + ", amber=" + amber + ", red=" + red + "]";
	}

}
