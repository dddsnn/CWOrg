package cworg.db;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.Tank;
import cworg.data.User;
import cworg.web.GetPlayerResponse;
import cworg.web.WgAccess;

@Stateless
public class DBAccessImpl implements DBAccess {
	@Inject
	EntityManager em;
	@EJB
	WgAccess wg;

	@Override
	public User findOrCreateUser(String accountId) {
		User user = em.find(User.class, accountId);
		if (user == null) {
			Player player = this.findOrCreatePlayer(accountId);
			user = new User(accountId, player);
			em.persist(user);
		}
		return user;
	}

	public Player findOrCreatePlayer(String accountId) {
		Player player = em.find(Player.class, accountId);
		if (player == null) {
			GetPlayerResponse playerResp = wg.getPlayer(accountId);
			player = new Player(accountId, playerResp.getCreationTime());
			player.setLastBattleTime(playerResp.getLastBattleTime());
			player.setLastLogoutTime(playerResp.getLastLogoutTime());
			player.setNick(playerResp.getNick());
			// persist the preliminary player, so we don't get a loop
			em.persist(player);
			// now get the related entities
			ClanMemberInformation clanMemberInfo =
					this.findOrCreateClanMemberInfo(playerResp.getClanId(),
							accountId);
			player.setClanInfo(clanMemberInfo);
			for (String tankId : playerResp.getTankIds()) {
				player.getTanks().add(this.findOrGetUpdateForTank(tankId));
			}
			// TODO do i need to merge now?
		}
		return player;
	}

	@Override
	public ClanMemberInformation findOrCreateClanMemberInfo(String clanId,
			String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tank findOrGetUpdateForTank(String tankId) {
		// TODO Auto-generated method stub
		return null;
	}
}
