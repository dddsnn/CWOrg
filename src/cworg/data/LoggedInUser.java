package cworg.data;

import java.io.Serializable;
import java.time.Instant;

public class LoggedInUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String accountId;
	private String accessToken;
	private Instant expiryTime;

	public LoggedInUser(String accountId, String accessToken, Instant expiryTime) {
		this.accountId = accountId;
		this.accessToken = accessToken;
		this.expiryTime = expiryTime;
	}

	/**
	 * Copy constructor for prolongating.
	 * @param user
	 * @param accessToken
	 * @param expiryTime
	 */
	public LoggedInUser(LoggedInUser user, String accessToken,
			Instant expiryTime) {
		this.accountId = user.getAccountId();
		this.accessToken = accessToken;
		this.expiryTime = expiryTime;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public Instant getExpiryTime() {
		return expiryTime;
	}
}
