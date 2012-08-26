package cworg.replay;

import java.util.Vector;

import cworg.Player;

public class Battle {
	private Vector<Player> team1 = new Vector<Player>();
	private Vector<Player> team2 = new Vector<Player>();
	private Map map = Map.UNKNOWN;
	private BattleMode mode = BattleMode.UNKNOWN;
}
