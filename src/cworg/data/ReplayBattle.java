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
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@IdClass(ReplayBattle.ReplayBattlePK.class)
public class ReplayBattle implements Serializable {
	public static class ReplayBattlePK {
		private long arenaId;
		private long player;

		public ReplayBattlePK() {
		}

		public ReplayBattlePK(long arenaId, long player) {
			this.arenaId = arenaId;
			this.player = player;
		}

		public long getArenaId() {
			return arenaId;
		}

		public long getPlayer() {
			return player;
		}
	}

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

	public ReplayBattle(long arenaId, Player player, BattleType battleType,
			boolean lockingEnabled, Instant arenaCreateTime, String mapName,
			Duration duration, int winningTeam, BattleOutcome outcome,
			Set<ReplayPlayer> team1, Set<ReplayPlayer> team2) {
		this.arenaId = arenaId;
		this.player = player;
		this.battleType = battleType;
		this.lockingEnabled = lockingEnabled;
		this.arenaCreateTime = arenaCreateTime;
		this.mapName = mapName;
		this.duration = duration;
		this.winningTeam = winningTeam;
		this.outcome = outcome;
		this.team1 = team1;
		this.team2 = team2;
	}

	public long getArenaId() {
		return arenaId;
	}

	public Player getPlayer() {
		return player;
	}

	public BattleType getBattleType() {
		return battleType;
	}

	public boolean isLockingEnabled() {
		return lockingEnabled;
	}

	public Instant getArenaCreateTime() {
		return arenaCreateTime;
	}

	public String getMapName() {
		return mapName;
	}

	public Duration getDuration() {
		return duration;
	}

	public int getWinningTeam() {
		return winningTeam;
	}

	public BattleOutcome getOutcome() {
		return outcome;
	}

	public Set<ReplayPlayer> getTeam1() {
		return team1;
	}

	public Set<ReplayPlayer> getTeam2() {
		return team2;
	}
}
