package cworg.data;

import java.io.Serializable;
import org.joda.time.DateTime;
import org.joda.time.Duration;

public class Tank implements Serializable {
	private static final long serialVersionUID = -6124006686650022014L;
	
	private TankType type;
	private String name;
	private String shortName;
	private int tier;
	private TankNation nation;
	private TankClass tankClass;
	private Duration freezeTime;
	private DateTime frozenFrom;
	private DateTime frozenUntil;
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