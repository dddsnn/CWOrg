package cworg.web;

import java.awt.Color;
import java.time.Instant;
import java.util.Set;

public class GetClanResponse {
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
	private Set<String> memberIds;

	public GetClanResponse(Color color, Instant creationTime, String creatorId,
			String description, boolean disbanded, String commanderId,
			int memberCount, String motto, String name, String clanTag,
			String globalMapEmblem24Url, String recruitingStationEmblem32Url,
			String recruitingStationEmblem64Url, String profileEmblem195Url,
			String tankEmblem64Url, String aircraftEmblem256Url,
			Set<String> memberIds) {
		this.color = color;
		this.creationTime = creationTime;
		this.creatorId = creatorId;
		this.description = description;
		this.disbanded = disbanded;
		this.commanderId = commanderId;
		this.memberCount = memberCount;
		this.motto = motto;
		this.name = name;
		this.clanTag = clanTag;
		this.globalMapEmblem24Url = globalMapEmblem24Url;
		this.recruitingStationEmblem32Url = recruitingStationEmblem32Url;
		this.recruitingStationEmblem64Url = recruitingStationEmblem64Url;
		this.profileEmblem195Url = profileEmblem195Url;
		this.tankEmblem64Url = tankEmblem64Url;
		this.aircraftEmblem256Url = aircraftEmblem256Url;
		this.memberIds = memberIds;
	}

	public Color getColor() {
		return color;
	}

	public Instant getCreationTime() {
		return creationTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDisbanded() {
		return disbanded;
	}

	public String getCommanderId() {
		return commanderId;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public String getMotto() {
		return motto;
	}

	public String getName() {
		return name;
	}

	public String getClanTag() {
		return clanTag;
	}

	public String getGlobalMapEmblem24Url() {
		return globalMapEmblem24Url;
	}

	public String getRecruitingStationEmblem32Url() {
		return recruitingStationEmblem32Url;
	}

	public String getRecruitingStationEmblem64Url() {
		return recruitingStationEmblem64Url;
	}

	public String getProfileEmblem195Url() {
		return profileEmblem195Url;
	}

	public String getTankEmblem64Url() {
		return tankEmblem64Url;
	}

	public String getAircraftEmblem256Url() {
		return aircraftEmblem256Url;
	}

	public Set<String> getMemberIds() {
		return memberIds;
	}
}
