package org.rythmos.oneconnect.json.response;

public class FeedbackJSONResponse {
	private String healthIndicator;

	private CommentsJSONResponse commentsJSONResponse;

	private String rating;

	public String getHealthIndicator() {
		return healthIndicator;
	}

	public void setHealthIndicator(String healthIndicator) {
		this.healthIndicator = healthIndicator;
	}

	public CommentsJSONResponse getComments() {
		return commentsJSONResponse;
	}

	public void setComments(CommentsJSONResponse commentsJSONResponse) {
		this.commentsJSONResponse = commentsJSONResponse;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ClassPojo [healthIndicator = " + healthIndicator + ", commentsJSONResponse = " + commentsJSONResponse
				+ ", rating = " + rating + "]";
	}
}
