package cworg.replay;

import cworg.data.TankType;

public class ReplayBattlePlayer {
	private String name;
	private String clanTag;
	private TankType tank;
	private boolean survived;

	public ReplayBattlePlayer(String name, String clanTag, TankType tank,
			boolean survived) {
		this.name = name;
		this.clanTag = clanTag;
		this.tank = tank;
		this.survived = survived;
	}

	public String getName() {
		return name;
	}

	public String getClanTag() {
		return clanTag;
	}

	public TankType getTank() {
		return tank;
	}

	public boolean isSurvived() {
		return survived;
	}
}
