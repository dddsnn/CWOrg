package cworg.data;

import org.joda.time.DateTime;

public class PlayerTank extends Tank {
	private static final long serialVersionUID = -5278177844431058940L;

	private TankStatus status = new TankStatus();;

	public DateTime getFrozenFrom() {
		return status.getFrozenFrom();
	}

	public void setFrozenFrom(DateTime frozenFrom) {
		status.setFrozenFrom(frozenFrom);
	}

	public DateTime getFrozenUntil() {
		return status.getFrozenUntil();
	}

	public void setFrozenUntil(DateTime frozenUntil) {
		status.setFrozenUntil(frozenUntil);
	}

	public boolean isInGarage() {
		return status.isInGarage();
	}

	public void setInGarage(boolean inGarage) {
		status.setInGarage(inGarage);
	}

	public TankStatus getStatus() {
		return status;
	}

	public void setStatus(TankStatus status) {
		this.status = status;
	}
}
