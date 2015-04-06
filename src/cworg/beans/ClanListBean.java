package cworg.beans;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import cworg.data.TankFreezeInformation;
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
	private Clan clan;

	public List<PlayerModel> getPlayers() {
		if (players != null)
			return players;
		User user = (User) session.getAttribute("user");
		clan = user.getPlayer().getClanInfo().getClan();
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

	public class PlayerModel {
		private Player player;

		public PlayerModel(Player player) {
			this.player = player;
		}

		public TankAvailability getTankAvailability(Tank tank) {
			TankAvailability res = TankAvailability.NO_BATTLES;
			Optional<PlayerTankInformation> tankInfoOpt =
					player.getTankInfos()
							.stream()
							.filter((tankInfo) -> tankInfo.getTank().equals(
									tank)).findFirst();
			boolean researched = tankInfoOpt.isPresent();
			if (researched) {
				res = TankAvailability.AVAILABLE;
				PlayerTankInformation tankInfo = tankInfoOpt.get();
				// TODO cache freeze info in a map (tank -> info)
				Optional<TankFreezeInformation> freezeInfoOpt =
						clan.getFreezeInfos()
								.stream()
								.filter((freezeInfo) -> freezeInfo
										.getTankInfo().equals(tankInfo))
								.findFirst();
				if (freezeInfoOpt.isPresent() && freezeInfoOpt.get().isFrozen()) {
					res = TankAvailability.FROZEN;
				}
			}
			return res;
		}

		public Instant getUnfreezeTime(Tank tank) {
			Optional<PlayerTankInformation> tankInfoOpt =
					player.getTankInfos()
							.stream()
							.filter((tankInfo) -> tankInfo.getTank().equals(
									tank)).findFirst();
			// TODO better throw exception?
			if (!tankInfoOpt.isPresent()) {
				return Instant.EPOCH;
			}
			Optional<TankFreezeInformation> freezeInfoOpt =
					clan.getFreezeInfos()
							.stream()
							.filter((freezeInfo) -> freezeInfo.getTankInfo()
									.equals(tankInfoOpt.get())).findFirst();
			if (!freezeInfoOpt.isPresent()) {
				return Instant.EPOCH;
			}
			return freezeInfoOpt.get().getUnfreezeTime();
		}

		public String getNick() {
			return player.getNick();
		}
	}

	public enum TankAvailability {
		AVAILABLE, NO_BATTLES, FROZEN
	}
}
