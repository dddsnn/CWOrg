package cworg.data;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = -6209042233812999452L;

	private long id = Player.INVALID_ID;
	private String name;
	private String password;
	private Player player;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
