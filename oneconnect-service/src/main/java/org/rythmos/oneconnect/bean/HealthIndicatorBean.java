package org.rythmos.oneconnect.bean;

import org.springframework.stereotype.Component;

@Component
public class HealthIndicatorBean {

	private Long type;
	private Float rating;
	private String action;
	private String feedback;

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	@Override
	public String toString() {
		return "HealthIndicatorBean [type=" + type + ", rating=" + rating + ", action=" + action + ", feedback="
				+ feedback + "]";
	}

}
