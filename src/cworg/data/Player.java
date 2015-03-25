package cworg.data;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ACCOUNT_ID")
	private long accountId;
	private Instant creationTime;
	private Instant lastBattleTime;
	private Instant lastLogoutTime;
	private String nick;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "CLANMEMBERINFO_ID")
	private ClanMemberInformation clanInfo;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "player",
			cascade = CascadeType.ALL)
	private Set<PlayerTankInformation> tankInfos;

	public Player() {
	}

	public Player(long accountId, Instant creationTime) {
		this.accountId = accountId;
		this.creationTime = creationTime;
		tankInfos = new HashSet<>();
	}

	public Instant getLastBattleTime() {
		return lastBattleTime;
	}

	public void setLastBattleTime(Instant lastBattleTime) {
		this.lastBattleTime = lastBattleTime;
	}

	public Instant getLastLogoutTime() {
		return lastLogoutTime;
	}

	public void setLastLogoutTime(Instant lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public ClanMemberInformation getClanInfo() {
		return clanInfo;
	}

	public void setClanInfo(ClanMemberInformation clanInfo) {
		this.clanInfo = clanInfo;
	}

	public long getAccountId() {
		return accountId;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public Set<PlayerTankInformation> getTanks() {
		return tankInfos;
	}
}
