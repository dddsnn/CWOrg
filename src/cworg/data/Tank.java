package cworg.data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;

public class Tank implements Serializable {
	// TODO yodatime
	private TankType type;
	private String name;
	private String shortName;
	private int tier;
	private TankNation nation;
	private TankClass tankClass;
	private Time freezeTime;
	private Calendar frozenFrom;
	private Calendar frozenUntil;
	private boolean inGarage;

	public TankType getType() {
		return type;
	}

	public void setType(TankType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public TankNation getNation() {
		return nation;
	}

	public void setNation(TankNation nation) {
		this.nation = nation;
	}

	public TankClass getTankClass() {
		return tankClass;
	}

	public void setTankClass(TankClass tankClass) {
		this.tankClass = tankClass;
	}

	public Time getFreezeTime() {
		return freezeTime;
	}

	public void setFreezeTime(Time freezeTime) {
		this.freezeTime = freezeTime;
	}

	public Calendar getFrozenFrom() {
		return frozenFrom;
	}

	public void setFrozenFrom(Calendar frozenFrom) {
		this.frozenFrom = frozenFrom;
	}

	public Calendar getFrozenUntil() {
		return frozenUntil;
	}

	public void setFrozenUntil(Calendar frozenUntil) {
		this.frozenUntil = frozenUntil;
	}

	public boolean isInGarage() {
		return inGarage;
	}

	public void setInGarage(boolean inGarage) {
		this.inGarage = inGarage;
	}

}