package cworg;

import java.io.Serializable;
import java.util.Vector;

import cworg.Tank;

public class Project implements Serializable {
	private String name;
	private Vector<Clan> clans = new Vector<Clan>();
	private Clan selectedClan = null;
	/**
	 * Which tanks are considered top tanks in this project and should be
	 * displayed in the table
	 */
	private Vector<TankType> displayedTanks;

	public Project(String name) {
		this.name = name;
	}

	Project(String name, Vector<TankType> displayedTanks) {
		this.name = name;
		this.displayedTanks = displayedTanks;
		if (displayedTanks == null)
			this.displayedTanks = new Vector<TankType>();
	}

	public static Vector<TankType> TOP_TIERS() {
		Vector<TankType> v = new Vector<TankType>();
		v = new Vector<TankType>();
		v.add(TankType.IS_7);
		v.add(TankType.IS_4);
		v.add(TankType.T110E5);
		v.add(TankType.MAUS);
		v.add(TankType.E_100);
		v.add(TankType.AMX_50B);
		v.add(TankType.T_62A);
		v.add(TankType.M48);
		v.add(TankType.E_50_M);
		v.add(TankType.BATCHAT);
		v.add(TankType.OBJECT_261);
		v.add(TankType.T92);
		v.add(TankType.GW_TYP_E);
		v.add(TankType.OBJECT_212);
		v.add(TankType.M40_M43);
		v.add(TankType.GW_TIGER);
		v.add(TankType.OBJECT_704);
		v.add(TankType.T110E3);
		v.add(TankType.T110E4);
		v.add(TankType.JPZ_E_100);
		v.add(TankType.AMX_50_FOCH_155);
		v.add(TankType.T_50_2);
		return v;
	}

	public static Vector<TankType> TOP_TANKS() {
		Vector<TankType> v = new Vector<TankType>();
		v = new Vector<TankType>();
		v.add(TankType.IS_7);
		v.add(TankType.IS_4);
		v.add(TankType.T110E5);
		v.add(TankType.MAUS);
		v.add(TankType.E_100);
		v.add(TankType.AMX_50B);
		v.add(TankType.T_62A);
		v.add(TankType.M48);
		v.add(TankType.E_50_M);
		v.add(TankType.BATCHAT);
		v.add(TankType.T_50_2);
		return v;
	}

	public static Vector<TankType> TOP_HEAVIES() {
		Vector<TankType> v = new Vector<TankType>();
		v = new Vector<TankType>();
		v.add(TankType.IS_7);
		v.add(TankType.IS_4);
		v.add(TankType.T110E5);
		v.add(TankType.MAUS);
		v.add(TankType.E_100);
		v.add(TankType.AMX_50B);
		return v;
	}

	public static Vector<TankType> ARTIES() {
		Vector<TankType> v = new Vector<TankType>();
		v = new Vector<TankType>();
		v.add(TankType.OBJECT_261);
		v.add(TankType.T92);
		v.add(TankType.GW_TYP_E);
		v.add(TankType.OBJECT_212);
		v.add(TankType.M40_M43);
		v.add(TankType.GW_TIGER);
		v.add(TankType.SU_14);
		v.add(TankType.S_51);
		v.add(TankType.M12);
		v.add(TankType.GW_PANTHER);
		v.add(TankType.SU_8);
		v.add(TankType.M41);
		v.add(TankType.HUMMEL);
		return v;
	}

	public void addClan(Clan newClan) throws IllegalArgumentException {
		for (Clan c : clans) {
			if (c.equals(newClan))
				throw new IllegalArgumentException(
						"Clan already exists in project");
		}
		if (clans.isEmpty())
			selectedClan = newClan;
		clans.add(newClan);
	}

	public Vector<Clan> getClans() {
		return clans;
	}

	public void setClans(Vector<Clan> clans) {
		this.clans = clans;
		if (!clans.contains(selectedClan))
			selectedClan = clans.isEmpty() ? null : clans.get(0);
	}

	public Vector<TankType> getDisplayedTanks() {
		return displayedTanks;
	}

	public void setDisplayedTanks(Vector<TankType> displayed_tanks) {
		this.displayedTanks = displayed_tanks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Clan getSelectedClan() {
		return selectedClan;
	}

	public void setSelectedClan(Clan selectedClan) {
		if (selectedClan != null)
			this.selectedClan = selectedClan;
	}

	/**
	 * Checks whether the players tanks are still frozen and updates their
	 * status if they have become unfrozen.
	 */
	public void refresh() {
		for (Clan c : clans) {
			for (Player p : c.getPlayers()) {
				for (Tank t : p.getTanks()) {
					t.getStatus().refresh();
				}
			}
		}
	}
}
