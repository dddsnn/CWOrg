package cworg.data;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
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
	private List<Long> lightDurationSeconds;
	@ElementCollection
	private List<Long> mediumDurationSeconds;
	@ElementCollection
	private List<Long> heavyDurationSeconds;
	@ElementCollection
	private List<Long> tdDurationSeconds;
	@ElementCollection
	private List<Long> spgDurationSeconds;
	private List<Duration> lightDurations;
	private List<Duration> mediumDurations;
	private List<Duration> heavyDurations;
	private List<Duration> tdDurations;
	private List<Duration> spgDurations;

	public FreezeDurations() {
	}

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
		this.lightDurationSeconds = makeDurationSeconds(lightDurations);
		this.mediumDurationSeconds = makeDurationSeconds(mediumDurations);
		this.heavyDurationSeconds = makeDurationSeconds(heavyDurations);
		this.tdDurationSeconds = makeDurationSeconds(tdDurations);
		this.spgDurationSeconds = makeDurationSeconds(spgDurations);
	}

	public String getName() {
		return name;
	}

	public List<Duration> getLightDurations() {
		if (lightDurations == null) {
			lightDurations = makeDurations(lightDurationSeconds);
		}
		return lightDurations;
	}

	public List<Duration> getMediumDurations() {
		if (mediumDurations == null) {
			mediumDurations = makeDurations(mediumDurationSeconds);
		}
		return mediumDurations;
	}

	public List<Duration> getHeavyDurations() {
		if (heavyDurations == null) {
			heavyDurations = makeDurations(heavyDurationSeconds);
		}
		return heavyDurations;
	}

	public List<Duration> getTdDurations() {
		if (tdDurations == null) {
			tdDurations = makeDurations(tdDurationSeconds);
		}
		return tdDurations;
	}

	public List<Duration> getSpgDurations() {
		if (spgDurations == null) {
			spgDurations = makeDurations(spgDurationSeconds);
		}
		return spgDurations;
	}

	private static List<Duration> makeDurations(List<Long> durationSeconds) {
		List<Duration> durations = new ArrayList<>(11);
		for (long seconds : durationSeconds) {
			durations.add(Duration.ofSeconds(seconds));
		}
		return durations;
	}

	private static List<Long> makeDurationSeconds(List<Duration> durations) {
		List<Long> durationSeconds = new ArrayList<>(11);
		for (Duration d : durations) {
			long seconds = d == null ? 0 : d.getSeconds();
			durationSeconds.add(seconds);
		}
		return durationSeconds;
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
