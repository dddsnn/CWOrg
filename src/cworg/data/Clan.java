package cworg.data;

import java.awt.Color;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Clan implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
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
}
