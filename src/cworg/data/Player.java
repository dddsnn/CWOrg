package cworg.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class Player implements Serializable {
	public static final long INVALID_ID = -1;
	private static final long serialVersionUID = 7493764929965791178L;

	private long id = Player.INVALID_ID;
	private String name;
	private Vector<Tank> tanks = new Vector<Tank>();
	private Calendar lastCW = null;

	public Player(String name) {
		this.name = name;
	}

	public Player(String name, long id) {
		this.name = name;
		this.id = id;
	}

	public void addTank(Tank newTank) {
		for (Tank t : tanks) {
			if (t.equals(newTank) && t.getType() != TankType.UNKNOWN)
				return;
		}
		tanks.add(newTank);
	}

	public Vector<Tank> getTanks() {
		return tanks;
	}

	public void setTanks(Vector<Tank> tanks) {
		this.tanks = tanks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

//	boolean hasTank(TankType t) {
//		return tanks.contains(new Tank(t));
//	}

	public Calendar getLastCW() {
		return lastCW;
	}

	public void setLastCW(Calendar lastCW) {
		this.lastCW = lastCW;
	}
}
