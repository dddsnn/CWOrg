package cworg;

import java.io.Serializable;
import java.util.Vector;

import cworg.Tank;

public class Project implements Serializable {
	private Clan clan;
	/**
	 * Which tanks are considered top tanks in this project and should be
	 * displayed in the table
	 */
	private Vector<TankType> displayed_tanks;

	Project(Clan clan) {
		this(clan, Project.TOP_TIERS());
	}

	Project(Clan clan, Vector<TankType> displayed_tanks) {
		this.clan = clan;
		if (clan == null)
			this.clan = new Clan("", "");
		this.displayed_tanks = displayed_tanks;
		if (displayed_tanks == null)
			this.displayed_tanks = new Vector<TankType>();
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
		v.add(TankType.T_54);
		v.add(TankType.PATTON);
		v.add(TankType.E_50);
		v.add(TankType.BATCHAT);
		v.add(TankType.OBJECT_261);
		v.add(TankType.T92);
		v.add(TankType.GW_TYP_E);
		v.add(TankType.OBJECT_212);
		v.add(TankType.M40_M43);
		v.add(TankType.GW_TIGER);
		v.add(TankType.OBJECT_704);
		v.add(TankType.T30);
		v.add(TankType.T95);
		v.add(TankType.JAGDTIGER);
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
		v.add(TankType.T_54);
		v.add(TankType.PATTON);
		v.add(TankType.E_50);
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

	public Clan getClan() {
		return clan;
	}

	public void setClan(Clan clan) {
		this.clan = clan;
	}

	public Vector<TankType> getDisplayedTanks() {
		return displayed_tanks;
	}

	public void setDisplayedTanks(Vector<TankType> displayed_tanks) {
		this.displayed_tanks = displayed_tanks;
	}

	/**
	 * Checks whether the players tanks are still frozen and updates their
	 * status if they have become unfrozen.
	 */
	public void refresh() {
		for (Player p : getClan().getPlayers()) {
			for (Tank t : p.getTanks()) {
				t.getStatus().refresh();
			}
		}
	}
}
