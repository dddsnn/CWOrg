package cworg.replay;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import cworg.data.ReplayBattle.BattleOutcome;
import cworg.data.ReplayBattle.BattleType;

public class ParseReplayResponse {
	public static class ParseReplayResponsePlayer {
		private String tankId;
		private boolean survived;

		public ParseReplayResponsePlayer(String tankId, boolean survived) {
			this.tankId = tankId;
			this.survived = survived;
		}

		public String getTankId() {
			return tankId;
		}

		public boolean isSurvived() {
			return survived;
		}
	}

	private long arenaId;
	private String playerId;
	private BattleType battleType;
	private boolean lockingEnabled;
	private Instant arenaCreateTime;
	private String mapName;
	private Duration duration;
	private int winningTeam;
	private BattleOutcome outcome;
	private Map<String, ParseReplayResponsePlayer> team1;
	private Map<String, ParseReplayResponsePlayer> team2;

	public ParseReplayResponse(long arenaId, String playerId,
			BattleType battleType, boolean lockingEnabled,
			Instant arenaCreateTime, String mapName, Duration duration,
			int winningTeam, BattleOutcome outcome,
			Map<String, ParseReplayResponsePlayer> team1,
			Map<String, ParseReplayResponsePlayer> team2) {
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

	public String getPlayerId() {
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

	public Map<String, ParseReplayResponsePlayer> getTeam1() {
		return team1;
	}

	public Map<String, ParseReplayResponsePlayer> getTeam2() {
		return team2;
	}
}
