package cworg.replay;

import java.util.Calendar;
import java.util.Vector;

public class ReplayBattle {
	private Vector<ReplayBattlePlayer> team1 = new Vector<ReplayBattlePlayer>();
	private Vector<ReplayBattlePlayer> team2 = new Vector<ReplayBattlePlayer>();
	private Calendar arenaCreateTime = null;

	public ReplayBattle(Vector<ReplayBattlePlayer> team1,
			Vector<ReplayBattlePlayer> team2, Calendar arenaCreateTime) {
		this.team1 = team1;
		this.team2 = team2;
		this.arenaCreateTime = arenaCreateTime;
	}

	public Vector<ReplayBattlePlayer> getTeam1() {
		return team1;
	}

	public Vector<ReplayBattlePlayer> getTeam2() {
		return team2;
	}

	public Calendar getArenaCreateTime() {
		return arenaCreateTime;
	}
}
