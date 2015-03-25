package cworg.web;

import java.time.Instant;

public class GetClanMemberInfoResponse {
	private long clanId;
	private Instant joinTime;
	private String role;
	private String internalRole;

	public GetClanMemberInfoResponse(long clanId, Instant joinTime,
			String role, String internalRole) {
		this.clanId = clanId;
		this.joinTime = joinTime;
		this.role = role;
		this.internalRole = internalRole;
	}

	public long getClanId() {
		return clanId;
	}

	public Instant getJoinTime() {
		return joinTime;
	}

	public String getRole() {
		return role;
	}

	public String getInternalRole() {
		return internalRole;
	}
}
