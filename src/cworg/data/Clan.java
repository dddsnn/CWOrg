package cworg.data;

import java.awt.Color;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Clan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long clanId;
	private Color color;
	private Instant creationTime;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATOR_ID")
	private Player creator;
	@Column(length = 1500)
	private String description;
	private boolean disbanded;
	private String commanderId;
	private int memberCount;
	private String motto;
	private String name;
	private String clanTag;
	private String globalMapEmblem24Url;
	private String recruitingStationEmblem32Url;
	private String recruitingStationEmblem64Url;
	private String tankEmblem64Url;
	private String aircraftEmblem256Url;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clan")
	private Set<ClanMemberInformation> members;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "owner",
			cascade = CascadeType.ALL)
	private Set<TankFreezeInformation> freezeInfos;

	public Clan() {
		this.members = new HashSet<>();
		this.freezeInfos = new HashSet<>();
	}

	public Clan(long clanId, Instant creationTime) {
		this.clanId = clanId;
		this.creationTime = creationTime;
		this.members = new HashSet<>();
		this.freezeInfos = new HashSet<>();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisbanded() {
		return disbanded;
	}

	public void setDisbanded(boolean disbanded) {
		this.disbanded = disbanded;
	}

	public String getCommanderId() {
		return commanderId;
	}

	public void setCommanderId(String commanderId) {
		this.commanderId = commanderId;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClanTag() {
		return clanTag;
	}

	public void setClanTag(String clanTag) {
		this.clanTag = clanTag;
	}

	public String getGlobalMapEmblem24Url() {
		return globalMapEmblem24Url;
	}

	public void setGlobalMapEmblem24Url(String globalMapEmblem24Url) {
		this.globalMapEmblem24Url = globalMapEmblem24Url;
	}

	public String getRecruitingStationEmblem32Url() {
		return recruitingStationEmblem32Url;
	}

	public void setRecruitingStationEmblem32Url(
			String recruitingStationEmblem32Url) {
		this.recruitingStationEmblem32Url = recruitingStationEmblem32Url;
	}

	public String getRecruitingStationEmblem64Url() {
		return recruitingStationEmblem64Url;
	}

	public void setRecruitingStationEmblem64Url(
			String recruitingStationEmblem64Url) {
		this.recruitingStationEmblem64Url = recruitingStationEmblem64Url;
	}

	public String getTankEmblem64Url() {
		return tankEmblem64Url;
	}

	public void setTankEmblem64Url(String tankEmblem64Url) {
		this.tankEmblem64Url = tankEmblem64Url;
	}

	public String getAircraftEmblem256Url() {
		return aircraftEmblem256Url;
	}

	public void setAircraftEmblem256Url(String aircraftEmblem256Url) {
		this.aircraftEmblem256Url = aircraftEmblem256Url;
	}

	public long getClanId() {
		return clanId;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public Player getCreator() {
		return creator;
	}

	public void setCreator(Player creator) {
		this.creator = creator;
	}

	public Set<ClanMemberInformation> getMembers() {
		return members;
	}

	public Set<TankFreezeInformation> getFreezeInfos() {
		return freezeInfos;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Clan))
			return false;
		Clan otherClan = (Clan) other;
		return this.getClanId() == otherClan.getClanId();
	}

	@Override
	public int hashCode() {
		return Long.hashCode(clanId);
	}
}
