package cworg.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PlayerTankInformation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "PLAYERTANKINFO_ID")
	private long id;
	@ManyToOne
	@JoinColumn(name = "TANK_ID")
	private Tank tank;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER_ID")
	private Player player;

	public PlayerTankInformation() {
	}

	public PlayerTankInformation(Tank tank, Player player) {
		this.tank = tank;
		this.player = player;
	}

	public Tank getTank() {
		return tank;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof PlayerTankInformation))
			return false;
		PlayerTankInformation otherTankInfo = (PlayerTankInformation) other;
		return this.id == otherTankInfo.id;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}
}
