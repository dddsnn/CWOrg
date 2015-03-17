package cworg.data;

import java.awt.Color;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Clan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String clanId;
	private Color color;
	private Instant creationTime;
	private String creatorId;
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
	private String profileEmblem195Url;
	private String tankEmblem64Url;
	private String aircraftEmblem256Url;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clan")
	private Set<ClanMemberInformation> members;

	public Clan(String clanId, Instant creationTime, String creatorId) {
		this.clanId = clanId;
		this.creationTime = creationTime;
		this.creatorId = creatorId;
		members = new HashSet<>();
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

	public String getProfileEmblem195Url() {
		return profileEmblem195Url;
	}

	public void setProfileEmblem195Url(String profileEmblem195Url) {
		this.profileEmblem195Url = profileEmblem195Url;
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

	public String getClanId() {
		return clanId;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public Set<ClanMemberInformation> getMembers() {
		return members;
	}
}
