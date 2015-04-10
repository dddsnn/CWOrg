package cworg.data;

import java.io.Serializable;
import java.time.Duration;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FreezeDurations implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String name;
	@ElementCollection
	private List<Duration> lightDurations;
	@ElementCollection
	private List<Duration> mediumDurations;
	@ElementCollection
	private List<Duration> heavyDurations;
	@ElementCollection
	private List<Duration> tdDurations;
	@ElementCollection
	private List<Duration> spgDurations;

	public FreezeDurations(String name, List<Duration> lightDurations,
			List<Duration> mediumDurations, List<Duration> heavyDurations,
			List<Duration> tdDurations, List<Duration> spgDurations) {
		if (name == null || lightDurations == null || mediumDurations == null
				|| heavyDurations == null || tdDurations == null
				|| spgDurations == null) {
			throw new IllegalArgumentException("Args can't be null");
		}
		this.name = name;
		this.lightDurations = lightDurations;
		this.mediumDurations = mediumDurations;
		this.heavyDurations = heavyDurations;
		this.tdDurations = tdDurations;
		this.spgDurations = spgDurations;
	}

	public String getName() {
		return name;
	}

	public List<Duration> getLightDurations() {
		return lightDurations;
	}

	public List<Duration> getMediumDurations() {
		return mediumDurations;
	}

	public List<Duration> getHeavyDurations() {
		return heavyDurations;
	}

	public List<Duration> getTdDurations() {
		return tdDurations;
	}

	public List<Duration> getSpgDurations() {
		return spgDurations;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof FreezeDurations))
			return false;
		FreezeDurations otherFd = (FreezeDurations) other;
		return name.equals(otherFd.getName());
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
