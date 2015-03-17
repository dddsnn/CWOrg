package cworg.db;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.Tank;
import cworg.data.User;
import cworg.web.GetClanMemberInfoResponse;
import cworg.web.GetClanResponse;
import cworg.web.GetPlayerResponse;
import cworg.web.WebException;
import cworg.web.WgAccess;
import cworg.web.WgApiError;

@Stateless
public class DBAccessImpl implements DBAccess {
	@Inject
	EntityManager em;
	@EJB
	WgAccess wg;

	@Override
	public User findOrCreateUser(String accountId) throws WebException,
			WgApiError {
		User user = em.find(User.class, accountId);
		if (user == null) {
			Player player = this.findOrCreatePlayer(accountId);
			user = new User(accountId, player);
			em.persist(user);
		}
		return user;
	}

	public Player findOrCreatePlayer(String accountId) throws WebException,
			WgApiError {
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
			ClanMemberInformation clanMemberInfo = null;
			if (playerResp.getClanId() != null) {
				clanMemberInfo = this.findOrCreateClanMemberInfo(player);
			}
			player.setClanInfo(clanMemberInfo);
			for (String tankId : playerResp.getTankIds()) {
				player.getTanks().add(this.findOrGetUpdateForTank(tankId));
			}
			// TODO do i need to merge now?
		}
		return player;
	}

	@Override
	public ClanMemberInformation findOrCreateClanMemberInfo(Player player)
			throws WebException, WgApiError {
		GetClanMemberInfoResponse cmInfoResp =
				wg.getClanMemberInfo(player.getAccountId());
		ClanMemberInformation cmInfo = new ClanMemberInformation(player);
		cmInfo.setInternalRole(cmInfoResp.getInternalRole());
		cmInfo.setJoinTime(cmInfoResp.getJoinTime());
		cmInfo.setRole(cmInfoResp.getRole());
		// persist to avoid a loop
		em.persist(cmInfo);
		Clan clan = this.findOrCreateClan(cmInfoResp.getClanId());
		cmInfo.setClan(clan);
		// TODO do i need to merge now?
		return cmInfo;
	}

	@Override
	public Tank findOrGetUpdateForTank(String tankId) throws WebException,
			WgApiError {
		Tank tank = em.find(Tank.class, tankId);
		if (tank == null) {
			// reload the whole tank database
			// TODO delete existing tanks first, probably lock the db for it
			Set<Tank> tanks = wg.getAllTankInfo();
			for (Tank t : tanks) {
				// TODO is this correct?
				em.merge(t);
			}
		}
		// TODO returns null if tank not found. better throw exception?
		return em.find(Tank.class, tankId);
	}

	@Override
	public Clan findOrCreateClan(String clanId) throws WebException, WgApiError {
		Clan clan = em.find(Clan.class, clanId);
		if (clan == null) {
			GetClanResponse clanResp = wg.getClan(clanId);
			clan =
					new Clan(clanId, clanResp.getCreationTime(),
							clanResp.getCreatorId());
			clan.setAircraftEmblem256Url(clanResp.getAircraftEmblem256Url());
			clan.setClanTag(clanResp.getClanId());
			clan.setColor(clanResp.getColor());
			clan.setCommanderId(clanResp.getCommanderId());
			clan.setDescription(clanResp.getDescription());
			clan.setDisbanded(clanResp.isDisbanded());
			clan.setGlobalMapEmblem24Url(clanResp.getGlobalMapEmblem24Url());
			clan.setMemberCount(clanResp.getMemberCount());
			clan.setMotto(clanResp.getMotto());
			clan.setName(clanResp.getName());
			clan.setProfileEmblem195Url(clanResp.getProfileEmblem195Url());
			clan.setRecruitingStationEmblem32Url(clanResp
					.getRecruitingStationEmblem32Url());
			clan.setRecruitingStationEmblem64Url(clanResp
					.getRecruitingStationEmblem64Url());
			clan.setTankEmblem64Url(clanResp.getTankEmblem64Url());
			// persist to avoid a loop
			em.persist(clan);
			for (String memberId : clanResp.getMemberIds()) {
				Player member = this.findOrCreatePlayer(memberId);
				clan.getMembers().add(member.getClanInfo());
			}
			// TODO do i need to merge now?
		}
		return clan;
	}
}
