package cworg.replay;

import java.util.Set;

import javax.persistence.Entity;

import cworg.data.Player;

@Entity
public class ReplayBattle {
	private long battleId;
	private Set<Player> team1;
	private Set<Player> team2;
	
}
