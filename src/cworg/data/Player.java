//package cworg.data;
//
//import java.io.Serializable;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Vector;
//
//public class Player implements Serializable, Comparable<Player> {
//	public static final long INVALID_ID = -1;
//	private static final long serialVersionUID = 7493764929965791178L;
//
//	private long id = Player.INVALID_ID;
//	private String name;
//	private List<PlayerTank> tanks = new Vector<PlayerTank>();
//	private Calendar lastCW = null;
//
//	public void addTank(PlayerTank newTank) {
//		for (PlayerTank t : tanks) {
//			if (t.equals(newTank) && t.getType() != TankType.UNKNOWN)
//				return;
//		}
//		tanks.add(newTank);
//	}
//
//	public List<PlayerTank> getTanks() {
//		return tanks;
//	}
//
//	public void setTanks(List<PlayerTank> tanks) {
//		this.tanks = tanks;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}
//
//	// boolean hasTank(TankType t) {
//	// return tanks.contains(new Tank(t));
//	// }
//
//	public Calendar getLastCW() {
//		return lastCW;
//	}
//
//	public void setLastCW(Calendar lastCW) {
//		this.lastCW = lastCW;
//	}
//
//	@Override
//	public int compareTo(Player o) {
//		if (getId() < o.getId())
//			return -1;
//		if (getId() == o.getId())
//			return 0;
//		else
//			return 1;
//	}
//}
