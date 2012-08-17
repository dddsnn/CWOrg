package cworg;

import java.io.Serializable;
import java.util.Vector;

public class Player implements Serializable {
	private Vector<Tank> tanks = new Vector<Tank>();
	private boolean is_banned = false;
	private boolean is_active = true;
	private String name;
	private long id = -1;

	public Player(String name) {
		this.name = name;
	}

	public Player(String name, long id) {
		this.name = name;
		this.id = id;
	}

	public void addTank(Tank newTank) {
		for (Tank t : tanks) {
			if (t.equals(newTank)
					&& t.getType() != TankType.UNKNOWN)
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	boolean hasTank(TankType t) {
		return tanks.contains(new Tank(t));
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Player))
			return false;
		Player p = (Player) o;
		return (p.getName().equals(this.getName()));
	}
}
