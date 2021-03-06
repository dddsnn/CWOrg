package cworg.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ReplayPlayer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "BATTLE_ARENA_ID",
					referencedColumnName = "ARENA_ID"),
			@JoinColumn(name = "BATTLE_PLAYER_ID",
					referencedColumnName = "PLAYER_ID") })
	private ReplayBattle battle;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLAYER")
	private Player player;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TANK")
	private Tank tank;
	private boolean survived;

	public ReplayPlayer() {
	}

	public ReplayPlayer(boolean survived) {
		this.survived = survived;
	}

	public ReplayBattle getBattle() {
		return battle;
	}

	public void setBattle(ReplayBattle battle) {
		this.battle = battle;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}

	public boolean isSurvived() {
		return survived;
	}
}
