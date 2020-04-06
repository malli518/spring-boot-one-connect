package org.rythmos.oneconnect.bean;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RAGStatusResponseBean {
	private List<ESummaryBean> red;
	private List<ESummaryBean> amber;
	private List<ESummaryBean> green;

	
	public List<ESummaryBean> getRed() {
		return red;
	}


	public void setRed(List<ESummaryBean> red) {
		this.red = red;
	}


	public List<ESummaryBean> getAmber() {
		return amber;
	}


	public void setAmber(List<ESummaryBean> amber) {
		this.amber = amber;
	}


	public List<ESummaryBean> getGreen() {
		return green;
	}


	public void setGreen(List<ESummaryBean> green) {
		this.green = green;
	}


	@Override
	public String toString() {
		return "RAGStatusResponseBean [red=" + red + ", amber=" + amber + ", green=" + green + "]";
	}

}
