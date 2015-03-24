package cworg.replay;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import cworg.data.Player;

@Entity
public class ReplayBattle implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum BattleOutcome {
		UNKNOWN, EXTERMINATION, CAPTURE, TIMEOUT, FAILURE, TECHNICAL
	}

	public enum BattleType {
		UNKNOWN, REGULAR, TRAINING, COMPANY, TOURNAMENT, CW, TUTORIAL,
		STRONGHOLD_SKIRMISH, STRONGHOLD_BATTLE
	}

	@Id
	private long arenaId;
	@Id
	@Column(name = "PLAYER_ID")
	private String playerId;
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId
	@JoinColumn(name = "PLAYER_ID")
	private Player player;
	private BattleType battleType;
	private boolean lockingEnabled;
	private Instant arenaCreateTime;
	private String mapName;
	private Duration duration;
	private int winningTeam;
	private BattleOutcome outcome;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "battle")
	private Set<ReplayPlayer> team1;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "battle")
	private Set<ReplayPlayer> team2;

}
