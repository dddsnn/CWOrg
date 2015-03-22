package cworg.data;

import java.io.Serializable;
import java.time.Instant;

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
	private Instant unfreezeTime;

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

	public Instant getUnfreezeTime() {
		return unfreezeTime;
	}

	public void setUnfreezeTime(Instant unfreezeTime) {
		this.unfreezeTime = unfreezeTime;
	}

	public boolean isFrozen() {
		if (unfreezeTime == null) {
			return false;
		} else if (unfreezeTime.isBefore(Instant.now())) {
			unfreezeTime = null;
			return false;
		} else {
			return true;
		}
	}
}
