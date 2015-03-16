package cworg.data;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ACCOUNT_ID")
	private String accountId;
	private Instant creationTime;
	private Instant lastBattleTime;
	private Instant lastLogoutTime;
	private String nick;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLANMEMBERINFO_ID")
	private ClanMemberInformation clanInfo;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PLAYER_TANK", joinColumns = { @JoinColumn(
			name = "PLAYER_ID", referencedColumnName = "ACCOUNT_ID") },
			inverseJoinColumns = { @JoinColumn(name = "TANK_ID",
					referencedColumnName = "ID") })
	private Set<Tank> tanks;
}
