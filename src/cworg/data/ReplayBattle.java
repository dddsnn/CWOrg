package cworg.data;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
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
	@Column(name = "ARENA_ID")
	private long arenaId;
	@OneToOne(fetch = FetchType.LAZY)
	@Id
	@JoinColumn(name = "PLAYER_ID")
	private Player player;
	private BattleType battleType;
	private boolean lockingEnabled;
	private Instant arenaCreateTime;
	private String mapName;
	private Duration duration;
	private int winningTeam;
	private BattleOutcome outcome;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "battle",
			cascade = CascadeType.ALL)
	private Set<ReplayPlayer> team1;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "battle",
			cascade = CascadeType.ALL)
	private Set<ReplayPlayer> team2;

	public ReplayBattle() {
	}

}
