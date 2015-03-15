package cworg.web;

import java.time.Instant;

public class ProlongateResponse {
	private String accessToken;
	private String accountId;
	private Instant expiryTime;

	public ProlongateResponse(String accessToken, String accountId,
			Instant expiryTime) {
		this.accessToken = accessToken;
		this.accountId = accountId;
		this.expiryTime = expiryTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccountId() {
		return accountId;
	}

	public Instant getExpiryTime() {
		return expiryTime;
	}
}
