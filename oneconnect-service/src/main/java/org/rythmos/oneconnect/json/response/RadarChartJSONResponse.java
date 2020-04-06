package org.rythmos.oneconnect.json.response;

import java.util.List;

public class RadarChartJSONResponse {

	private List<String> data;

	private String label;

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "RadarChartJSONResponse [data=" + data + ", label=" + label + "]";
	}

}
