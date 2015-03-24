package cworg.replay;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cworg.data.Player;
import cworg.data.Tank;

@Entity
public class ReplayPlayer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BATTLE")
	private ReplayBattle battle;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER")
	private Player player;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TANK")
	private Tank tank;
	private boolean survived;
}
