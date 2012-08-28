package cworg;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

public class Player implements Serializable {
	private String name;
	private Vector<Tank> tanks = new Vector<Tank>();
	private Calendar unavailableStart = null;
	private Calendar unavailableEnd = null;
	private long id = -1;
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

	boolean hasTank(TankType t) {
		return tanks.contains(new Tank(t));
	}

	public Calendar getUnavailableStart() {
		return unavailableStart;
	}

	public void setUnavailableStart(Calendar unavailableStart) {
		this.unavailableStart = unavailableStart;
	}

	public Calendar getUnavailableEnd() {
		return unavailableEnd;
	}

	public void setUnavailableEnd(Calendar unavailableEnd) {
		this.unavailableEnd = unavailableEnd;
	}

	public Calendar getLastCW() {
		return lastCW;
	}

	public void setLastCW(Calendar lastCW) {
		this.lastCW = lastCW;
	}

	public boolean isAvailable() {
		if (unavailableStart == null && unavailableEnd == null)
			return true;
		Calendar now = Calendar.getInstance();
		if (unavailableEnd != null) {
			if (unavailableEnd.before(now)) {
				unavailableStart = null;
				unavailableEnd = null;
				return true;
			} else
				return false;
		}
		if (unavailableStart != null) {
			if (unavailableStart.after(now))
				return true;
			else
				return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Player))
			return false;
		Player p = (Player) o;
		return (p.getName().equals(this.getName()));
	}
}
