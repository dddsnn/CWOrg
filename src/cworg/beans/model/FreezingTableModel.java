package cworg.beans.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cworg.data.Player;
import cworg.data.PlayerTank;
import cworg.data.Tank;
import cworg.data.TankStatus;
import cworg.data.TankType;

@ManagedBean
@SessionScoped
public class FreezingTableModel {
	private List<Player> players;
	private List<Tank> displayedTanks;

	public FreezingTableModel() {
		players = new ArrayList<Player>();
		Player p = new Player();
		p.setName("PlayerName1");
		PlayerTank pt = new PlayerTank();
		TankStatus status = new TankStatus();
		pt.setStatus(status);
		pt.setType(TankType.IS_4);
		p.addTank(pt);
		players.add(p);

		displayedTanks = new ArrayList<Tank>();
		Tank t = new Tank();
		t.setType(TankType.IS_4);
		t.setShortName("TankName1");
		displayedTanks.add(t);
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Map<Player, List<TankStatus>> getStatusMap() {
		Map<Player, List<TankStatus>> statusMap =
				new TreeMap<Player, List<TankStatus>>();
		for (Player p : getPlayers()) {
			List<TankStatus> statusList = new LinkedList<TankStatus>();
			for (Tank t : getDisplayedTanks()) {
				for (PlayerTank pt : p.getTanks()) {
					if (pt.getType() == t.getType()) {
						statusList.add(pt.getStatus());
					}
				}
			}
			statusMap.put(p, statusList);
		}
		return statusMap;
	}

	public List<Tank> getDisplayedTanks() {
		return displayedTanks;

	}

	public void setDisplayedTanks(List<Tank> displayedTanks) {
		this.displayedTanks = displayedTanks;
	}
}
