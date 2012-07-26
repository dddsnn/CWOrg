package cworg;

import java.io.Serializable;
import java.util.Vector;

public class Player implements Serializable {
	private Vector<Tank> tanks;
	private boolean is_banned;
	private boolean is_active;
	private String name;

	Player(String name) {
		this.name = name;
		tanks = new Vector<Tank>();
		is_banned = false;
		is_active = true;
	}

	public Vector<Tank> getTanks() {
		return tanks;
	}

	public void setTanks(Vector<Tank> tanks) {
		this.tanks = tanks;
	}

	public boolean isBanned() {
		return is_banned;
	}

	public void setBanned(boolean is_banned) {
		this.is_banned = is_banned;
	}

	public boolean isActive() {
		return is_active;
	}

	public void setActive(boolean is_active) {
		this.is_active = is_active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	boolean hasTank(TankType t) {
		return tanks.contains(new Tank(t));
	}
}
