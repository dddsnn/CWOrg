package cworg.beans;

import java.util.ArrayList;
import java.util.List;

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
		tanks = l;
		return l;
	}

	public static class PlayerModel {
		private Player player;

		public PlayerModel(Player player) {
			this.player = player;
		}

		public boolean isTankAvailable(Tank tank) {
			return player.getTankInfos().stream()
					.anyMatch((tankInfo) -> tankInfo.getTank().equals(tank));
		}

		public String getNick() {
			return player.getNick();
		}
	}
}
