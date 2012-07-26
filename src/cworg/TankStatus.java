package cworg;

import java.awt.Color;
import java.io.Serializable;
import java.util.Calendar;

public class TankStatus implements Serializable {
	private TankType type;
	private boolean is_frozen;
	private Calendar frozen_from;
	private Calendar frozen_until;
	private boolean in_garage;

	TankStatus(TankType type) {
		this.type = type;
		is_frozen = false;
		frozen_until = null;
		frozen_from = null;
		in_garage = true;
	}

	void setFrozenFrom(Calendar start) {
		frozen_from = (Calendar) start.clone();
		frozen_until = start;
		switch (Tank.getTankClass(type)) {
		case HEAVY:
			switch (Tank.getTankTier(type)) {
			case 10:
				frozen_until.add(Calendar.HOUR_OF_DAY, 168);
				break;
			case 9:
				frozen_until.add(Calendar.HOUR_OF_DAY, 120);
				break;
			}
			break;
		case MEDIUM:
			switch (Tank.getTankTier(type)) {
			case 9:
				frozen_until.add(Calendar.HOUR_OF_DAY, 96);
				break;
			case 8:
				frozen_until.add(Calendar.HOUR_OF_DAY, 72);
				break;
			}
			break;
		case LIGHT:
			switch (Tank.getTankTier(type)) {
			case 7:
			case 6:
			case 5:
				frozen_until.add(Calendar.HOUR_OF_DAY, 16);
				break;
			}
			break;
		case TD:
			switch (Tank.getTankTier(type)) {
			case 9:
				frozen_until.add(Calendar.HOUR_OF_DAY, 96);
				break;
			case 8:
				frozen_until.add(Calendar.HOUR_OF_DAY, 72);
				break;
			}
			break;
		case SPG:
			switch (Tank.getTankTier(type)) {
			case 8:
				frozen_until.add(Calendar.HOUR_OF_DAY, 74);
				break;
			case 7:
				frozen_until.add(Calendar.HOUR_OF_DAY, 50);
				break;
			case 6:
				frozen_until.add(Calendar.HOUR_OF_DAY, 36);
				break;
			case 5:
				frozen_until.add(Calendar.HOUR_OF_DAY, 27);
				break;
			}
			break;
		}

		if (frozen_until.before(Calendar.getInstance())) {
			frozen_until = null;
			frozen_from = null;
			is_frozen = false;
			return;
		}

		is_frozen = true;
	}

	public boolean isFrozen() {
		return is_frozen;
	}

	public void setFrozen(boolean isFrozen) {
		is_frozen = isFrozen;
	}

	public Calendar getFrozenFrom() {
		return frozen_from;
	}

	public Calendar getFrozenUntil() {
		return frozen_until;
	}

	private void setFrozenUntil(Calendar frozenUntil) {
		frozen_until = frozenUntil;
	}

	public boolean isInGarage() {
		return in_garage;
	}

	public void setInGarage(boolean inGarage) {
		in_garage = inGarage;
	}

	public Color getColor() {
		if (!isInGarage())
			return CWOrg.TANK_NOT_IN_GARAGE;
		if (!isFrozen())
			return CWOrg.TANK_AVAILABLE;

		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.HOUR_OF_DAY, 24);
		// frozen for more than 24 hours
		if (isFrozen() && getFrozenUntil().after(tomorrow))
			return CWOrg.TANK_FROZEN_LONG;
		// frozen for less than 24 hours
		return CWOrg.TANK_FROZEN_SHORT;
	}

	@Override
	public String toString() {
		if (isFrozen() && getFrozenUntil() != null) {
			Calendar tomorrow = Calendar.getInstance();
			tomorrow.add(Calendar.HOUR_OF_DAY, 24);
			// frozen for more than 24 hours -> display date of
			// unfreezing
			if (getFrozenUntil().after(tomorrow)) {
				int day = getFrozenUntil().get(Calendar.DATE);
				int month = (getFrozenUntil().get(
						Calendar.MONTH) + 1);
				return day + "." + month;
			}
			// frozen for less than 24 hours -> display time of
			// unfreezing
			else {
				int hour = getFrozenUntil().get(
						Calendar.HOUR_OF_DAY);
				int minute = getFrozenUntil().get(
						Calendar.MINUTE);
				return hour + ":" + minute;
			}
		}
		// not frozen -> no text
		return "";
	}

	public void refresh() {
		Calendar now = Calendar.getInstance();
		if (isFrozen() && now.after(getFrozenUntil())) {
			setFrozen(false);
			setFrozenFrom(null);
			setFrozenUntil(null);
		}
	}
}
