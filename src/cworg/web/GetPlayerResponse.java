package cworg.web;

import java.time.Instant;
import java.util.Set;

public class GetPlayerResponse {
	private String accountId;
	private Instant creationTime;
	private Instant lastBattleTime;
	private Instant lastLogoutTime;
	private String nick;
	private String clanId;
	private Set<String> tankIds;

	public GetPlayerResponse(String accountId, Instant creationTime,
			Instant lastBattleTime, Instant lastLogoutTime, String nick,
			String clanId, Set<String> tankIds) {
		this.accountId = accountId;
		this.creationTime = creationTime;
		this.lastBattleTime = lastBattleTime;
		this.lastLogoutTime = lastLogoutTime;
		this.nick = nick;
		this.clanId = clanId;
		this.tankIds = tankIds;
	}

	public String getAccountId() {
		return accountId;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public Instant getLastBattleTime() {
		return lastBattleTime;
	}

	public Instant getLastLogoutTime() {
		return lastLogoutTime;
	}

	public String getNick() {
		return nick;
	}

	public String getClanId() {
		return clanId;
	}

	public Set<String> getTankIds() {
		return tankIds;
	}
}
