package cworg.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.PlayerTankInformation;
import cworg.data.Tank;
import cworg.data.User;
import cworg.web.WgAccess;

@RequestScoped
@Named("clanListBean")
public class ClanListBean {
	@Inject
	private HttpSession session;
	@EJB
	private WgAccess wg;
	@PersistenceContext
	private EntityManager em;
	private List<PlayerModel> players;
	private List<Tank> tanks;

	public List<PlayerModel> getPlayers() {
		if (players != null)
			return players;
		User user = (User) session.getAttribute("user");
		Clan clan = user.getPlayer().getClanInfo().getClan();
		List<PlayerModel> res = new ArrayList<>();
		for (ClanMemberInformation cm : clan.getMembers()) {
			Player p = cm.getPlayer();
			res.add(new PlayerModel(p));
		}
		players = res;
		return res;
	}

	public List<Tank> getTanks() {
		if (tanks != null)
			return tanks;
		List<Tank> l =
				em.createNamedQuery("findAllTanks", Tank.class).getResultList();
		List<Tank> res = new ArrayList<>(l);
		// List<Tank> res = new ArrayList<>();
		// res.add(l.get(0));
		tanks = res;
		return res;
	}

	// public Set<Map<Tank, String>> getMaps() {
	// Set<Map<Tank, String>> maps = new HashSet<>();
	// for (Player p : this.getPlayers()) {
	// Map<Tank, String> map = new HashMap<>();
	// for (Tank t : this.getTanks()) {
	// String s = p.getTanks().contains(t) ? "yea" : "nope";
	// map.put(t, s);
	// }
	// maps.add(map);
	// }
	// return maps;
	// }

	public List<Map<String, String>> getLom() {
		List<Map<String, String>> res = new ArrayList<>();
		Map<String, String> one = new HashMap<>();
		one.put("a", "one a");
		one.put("b", "one b");
		one.put("c", "one c");
		Map<String, String> two = new HashMap<>();
		one.put("a", "two a");
		one.put("b", "two b");
		one.put("c", "two c");
		res.add(one);
		res.add(two);
		return res;
	}

	public static class PlayerModel {
		private Player player;

		public PlayerModel(Player player) {
			this.player = player;
		}

		public boolean tankAvailable(Tank tank) {
			boolean res = false;
			for (PlayerTankInformation tankInfo : player.getTankInfos()) {
				if (tankInfo.getTank().equals(tank))
					res = true;
			}
			return res;
			// return player.getTankInfos().stream()
			// .anyMatch((tankInfo) -> tankInfo.getTank().equals(tank));
		}

		public String getNick() {
			return player.getNick();
		}
	}
}
