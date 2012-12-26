package cworg.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import cworg.data.Player;
import cworg.data.Tank;
import cworg.data.TankStatus;

@ManagedBean
public class FreezingTableBean {
	private List<Player> players;
	private List<Tank> displayedTanks;

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Map<Player, List<TankStatus>> getStatusMap() {
		// TODO
		return null;
	}

	public List<Tank> getDisplayedTanks() {
		displayedTanks = new ArrayList<Tank>();
		Tank t = new Tank();
		t.setShortName("TankName1");
		displayedTanks.add(t);
		return displayedTanks;

	}

	public void setDisplayedTanks(List<Tank> displayedTanks) {
		this.displayedTanks = displayedTanks;
	}
}
