package cworg.data;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TankFreezeInformation {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TANKINFO")
	private PlayerTankInformation tankInfo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER")
	private Clan owner;
	private Instant unfreezeTime;

	public TankFreezeInformation() {
	}

	public TankFreezeInformation(PlayerTankInformation tankInfo, Clan owner) {
		this.tankInfo = tankInfo;
		this.owner = owner;
	}

	public PlayerTankInformation getTankInfo() {
		return tankInfo;
	}

	public Clan getOwner() {
		return owner;
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
