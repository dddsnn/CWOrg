package cworg;

import java.io.Serializable;
import java.util.Vector;

public class Clan implements Serializable {
	private String clantag;
	private String name;
	private Vector<Player> players;

	Clan(String clantag, String name) {
		this.clantag = clantag;
		this.name = name;
		players = new Vector<Player>();
	}

	public static Clan loadData(String clantag) {

		return null;
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
}
