package cworg.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tank implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
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

	public Tank(String id, String name, String shortName, String type,
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

	public String getId() {
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

}
