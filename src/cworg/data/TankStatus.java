package cworg.data;

import java.io.Serializable;

import org.joda.time.DateTime;


public class TankStatus implements Serializable {
	private static final long serialVersionUID = -123317123463820890L;
	
	private DateTime frozenFrom;
	private DateTime frozenUntil;
	private boolean inGarage;

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
