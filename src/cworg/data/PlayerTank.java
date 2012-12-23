package cworg.data;

import org.joda.time.DateTime;
import org.joda.time.Duration;

public class PlayerTank extends Tank {
	private static final long serialVersionUID = -5278177844431058940L;
	
	private Duration freezeTime;
	private DateTime frozenFrom;
	private DateTime frozenUntil;
	private boolean inGarage;

	public Duration getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Duration freezeTime) {
		this.freezeTime = freezeTime;
	}

	public DateTime getFrozenFrom() {
		return frozenFrom;
	}

	public void setFrozenFrom(DateTime frozenFrom) {
		this.frozenFrom = frozenFrom;
	}

	public DateTime getFrozenUntil() {
		return frozenUntil;
	}

	public void setFrozenUntil(DateTime frozenUntil) {
		this.frozenUntil = frozenUntil;
	}

	public boolean isInGarage() {
		return inGarage;
	}

	public void setInGarage(boolean inGarage) {
		this.inGarage = inGarage;
	}
}
