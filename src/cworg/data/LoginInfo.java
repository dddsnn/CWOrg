package cworg.data;

import java.time.Instant;

public class LoginInfo {
	private String accessToken;
	private Instant expiryTime;

	public String getAccessToken() {
		return accessToken;
	}

	public Instant getExpiryTime() {
		return expiryTime;
	}

	public LoginInfo(String accessToken, Instant expiryTime) {
		super();
		this.accessToken = accessToken;
		this.expiryTime = expiryTime;
	}
}
