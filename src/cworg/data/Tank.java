package cworg.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
		@NamedQuery(name = "findAllTanks", query = "select t from Tank t"),
		@NamedQuery(name = "findTanksByTier",
				query = "select t from Tank t where t.tier = :tier") })
@Entity
public class Tank implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private long id;
	private String name;
	private String shortName;
	private String type;
	private String nation;
	private int tier;
	private String imageUrl;
	private String smallImageUrl;
	private String contourImageUrl;
	private boolean premium;
	private String internalName;
	private String internalNation;
	private String internalType;

	public Tank() {
	}

	public Tank(long id, String name, String shortName, String type,
			String nation, int tier, String imageUrl, String smallImageUrl,
			String contourImageUrl, boolean premium, String internalName,
			String internalNation, String internalType) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.type = type;
		this.nation = nation;
		this.tier = tier;
		this.imageUrl = imageUrl;
		this.smallImageUrl = smallImageUrl;
		this.contourImageUrl = contourImageUrl;
		this.premium = premium;
		this.internalName = internalName;
		this.internalNation = internalNation;
		this.internalType = internalType;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getType() {
		return type;
	}

	public String getNation() {
		return nation;
	}

	public int getTier() {
		return tier;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public String getContourImageUrl() {
		return contourImageUrl;
	}

	public boolean isPremium() {
		return premium;
	}

	public String getInternalName() {
		return internalName;
	}

	public String getInternalNation() {
		return internalNation;
	}

	public String getInternalType() {
		return internalType;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Tank))
			return false;
		Tank otherTank = (Tank) other;
		return this.getId() == otherTank.getId();
	}

	@Override
	public int hashCode() {
		return Long.hashCode(id);
	}
}
