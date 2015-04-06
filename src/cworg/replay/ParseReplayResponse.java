package cworg.replay;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import cworg.data.ReplayBattle.BattleOutcome;
import cworg.data.ReplayBattle.BattleType;

public class ParseReplayResponse {
	public static class ParseReplayResponsePlayer {
		private long tankId;
		private boolean survived;

		public ParseReplayResponsePlayer(long tankId, boolean survived) {
			this.tankId = tankId;
			this.survived = survived;
		}

		public long getTankId() {
			return tankId;
		}

		public boolean isSurvived() {
			return survived;
		}
	}

	private long arenaId;
	private long playerId;
	private BattleType battleType;
	private boolean lockingEnabled;
	private Instant arenaCreateTime;
	private String mapName;
	private Duration duration;
	private int winningTeam;
	private BattleOutcome outcome;
	private Map<Long, ParseReplayResponsePlayer> team1;
	private Map<Long, ParseReplayResponsePlayer> team2;

	public ParseReplayResponse(long arenaId, long playerId,
			BattleType battleType, boolean lockingEnabled,
			Instant arenaCreateTime, String mapName, Duration duration,
			int winningTeam, BattleOutcome outcome,
			Map<Long, ParseReplayResponsePlayer> team1,
			Map<Long, ParseReplayResponsePlayer> team2) {
		this.arenaId = arenaId;
		this.playerId = playerId;
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

	public long getPlayerId() {
		return playerId;
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

	public Map<Long, ParseReplayResponsePlayer> getTeam1() {
		return team1;
	}

	public Map<Long, ParseReplayResponsePlayer> getTeam2() {
		return team2;
	}
}
