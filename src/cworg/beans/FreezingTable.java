package cworg.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cworg.data.Player;
import cworg.data.Tank;

@ManagedBean
@SessionScoped
public class FreezingTable {
	private List<Player> players;
	private List<Tank> displayedTanks;

	public List<Player> getPlayers() {
		players = new ArrayList<Player>();
		Player p = new Player();
		p.setName("PlayerName1");
		List<Tank> tanks = new ArrayList<Tank>();
		Tank t = new Tank();
		t.setShortName("asdwdefegregr");
		tanks.add(t);
		players.add(p);
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
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
