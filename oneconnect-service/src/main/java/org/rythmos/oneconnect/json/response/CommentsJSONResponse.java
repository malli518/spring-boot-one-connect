package org.rythmos.oneconnect.json.response;

public class CommentsJSONResponse {
	private String rythmosAction;

	private String stakeholderFeedback;

	public String getRythmosAction() {
		return rythmosAction;
	}

	public void setRythmosAction(String rythmosAction) {
		this.rythmosAction = rythmosAction;
	}

	public String getStakeholderFeedback() {
		return stakeholderFeedback;
	}

	public void setStakeholderFeedback(String stakeholderFeedback) {
		this.stakeholderFeedback = stakeholderFeedback;
	}

	@Override
	public String toString() {
		return "CommentsJSONResponse [rythmosAction=" + rythmosAction + ", stakeholderFeedback=" + stakeholderFeedback
				+ "]";
	}

}
