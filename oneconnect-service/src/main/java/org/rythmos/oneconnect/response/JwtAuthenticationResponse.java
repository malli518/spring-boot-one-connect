package org.rythmos.oneconnect.response;

import org.rythmos.oneconnect.json.response.AvatarJSONResponse;

public class JwtAuthenticationResponse {
	private String accessToken;
	private String tokenType = "Bearer";

	private AvatarJSONResponse avatar;

	public JwtAuthenticationResponse(String accessToken, AvatarJSONResponse avatar) {
		super();
		this.accessToken = accessToken;
		this.avatar = avatar;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public AvatarJSONResponse getAvatar() {
		return avatar;
	}

	public void setAvatar(AvatarJSONResponse avatar) {
		this.avatar = avatar;
	}

}
