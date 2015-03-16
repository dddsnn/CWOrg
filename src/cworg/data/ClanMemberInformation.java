package cworg.data;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ClanMemberInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="CLANMEMBERINFO_ID")
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAN_ID")
	private Clan clan;
	private Instant joinTime;
	private String role;
	private String internalRole;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "clanInfo")
	private Player player;
}
