package cworg.data;

import java.io.Serializable;
import java.util.Vector;

public class Clan implements Serializable {
	public static final long INVALID_ID = -1;

	private String clantag;
	private String name;
	private Vector<Player> players = new Vector<Player>();
	private long id = Clan.INVALID_ID;

	public Clan(String clantag, String name) {
		this.clantag = clantag;
		this.name = name;
	}

	public Clan(String clantag, String name, long id) {
		this.clantag = clantag;
		this.name = name;
		this.id = id;
	}

	// public static Clan loadData(String clantag) {

	// return null;
	// }

	public void addPlayer(Player newPlayer) {
		for (Player p : players) {
			if (p.equals(newPlayer))
				return;
		}
		players.add(newPlayer);
	}

	public String getClantag() {
		return clantag;
	}

	public void setClantag(String clantag) {
		this.clantag = clantag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlayers(Vector<Player> players) {
		this.players = players;
	}

	public Vector<Player> getPlayers() {
		return players;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Clan))
			return false;
		Clan c = (Clan) o;
		return (c.getClantag().equals(this.getClantag()));
	}
}
