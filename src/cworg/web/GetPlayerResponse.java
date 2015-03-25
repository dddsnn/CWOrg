package cworg.web;

import java.time.Instant;
import java.util.Set;

public class GetPlayerResponse {
	private Instant creationTime;
	private Instant lastBattleTime;
	private Instant lastLogoutTime;
	private String nick;
	private long clanId;
	private Set<Long> tankIds;

	public GetPlayerResponse(Instant creationTime, Instant lastBattleTime,
			Instant lastLogoutTime, String nick, long clanId,
			Set<Long> tankIds) {
		this.creationTime = creationTime;
		this.lastBattleTime = lastBattleTime;
		this.lastLogoutTime = lastLogoutTime;
		this.nick = nick;
		this.clanId = clanId;
		this.tankIds = tankIds;
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

	public long getClanId() {
		return clanId;
	}

	public Set<Long> getTankIds() {
		return tankIds;
	}
}
