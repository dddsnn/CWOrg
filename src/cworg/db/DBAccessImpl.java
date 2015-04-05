package cworg.db;

import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.PlayerTankInformation;
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
	@PersistenceContext
	EntityManager em;
	@EJB
	WgAccess wg;

	@Override
	public User findOrCreateUser(long accountId) throws WebException,
			WgApiError {
		User user = em.find(User.class, accountId);
		if (user == null) {
			Player player = this.findOrCreatePlayer(accountId);
			user = new User(accountId, player);
			em.persist(user);
		}
		return user;
	}

	public Player findOrCreatePlayer(long accountId) throws WebException,
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
			for (long tankId : playerResp.getTankIds()) {
				player.getTanks()
						.add(this.createPlayerTankInfo(player, tankId));
			}
			if (playerResp.getClanId() != 0) {
				// update the clan, let it set the clanmember info
				this.updateOrCreateClan(playerResp.getClanId());
			}
			// TODO do i need to merge now?
		}
		return player;
	}

	private ClanMemberInformation createClanMemberInfo(Player player, Clan clan)
			throws WebException, WgApiError {
		GetClanMemberInfoResponse cmInfoResp =
				wg.getClanMemberInfo(player.getAccountId());
		ClanMemberInformation cmInfo = new ClanMemberInformation(player);
		cmInfo.setClan(clan);
		cmInfo.setInternalRole(cmInfoResp.getInternalRole());
		cmInfo.setJoinTime(cmInfoResp.getJoinTime());
		cmInfo.setRole(cmInfoResp.getRole());
		// persist to avoid a loop
		em.persist(cmInfo);
		// TODO do i need to merge now?
		return cmInfo;
	}

	@Override
	public Tank findOrGetUpdateForTank(long tankId) throws WebException,
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
	public Clan findOrCreateClan(long clanId) throws WebException, WgApiError {
		Clan clan = em.find(Clan.class, clanId);
		if (clan == null) {
			clan = this.createClan(clanId);
		}
		return clan;
	}

	private Clan updateOrCreateClan(long clanId) throws WebException,
			WgApiError {
		Clan clan = em.find(Clan.class, clanId);
		if (clan == null) {
			clan = this.createClan(clanId);
		}
		return clan;
	}

	private Clan createClan(long clanId) throws WebException, WgApiError {
		GetClanResponse clanResp = wg.getClan(clanId);
		Clan clan = new Clan(clanId, clanResp.getCreationTime());
		clan.setAircraftEmblem256Url(clanResp.getAircraftEmblem256Url());
		clan.setClanTag(clanResp.getClanTag());
		clan.setColor(clanResp.getColor());
		clan.setCommanderId(clanResp.getCommanderId());
		clan.setDescription(clanResp.getDescription());
		clan.setDisbanded(clanResp.isDisbanded());
		clan.setGlobalMapEmblem24Url(clanResp.getGlobalMapEmblem24Url());
		clan.setMemberCount(clanResp.getMemberCount());
		clan.setMotto(clanResp.getMotto());
		clan.setName(clanResp.getName());
		clan.setRecruitingStationEmblem32Url(clanResp
				.getRecruitingStationEmblem32Url());
		clan.setRecruitingStationEmblem64Url(clanResp
				.getRecruitingStationEmblem64Url());
		clan.setTankEmblem64Url(clanResp.getTankEmblem64Url());
		// persist to avoid a loop
		em.persist(clan);
		for (long memberId : clanResp.getMemberIds()) {
			Player member = this.findOrCreatePlayer(memberId);
			if (member.getClanInfo() == null
					|| !member.getClanInfo().getClan().equals(clan)) {
				// player joined recently, add clan member info
				member.setClanInfo(this.createClanMemberInfo(member, clan));
			}
			clan.getMembers().add(member.getClanInfo());
		}
		clan.setCreator(this.findOrCreatePlayer(clanResp.getCreatorId()));

		return clan;
	}

	@Override
	public PlayerTankInformation createPlayerTankInfo(Player player, long tankId)
			throws WebException, WgApiError {
		PlayerTankInformation tankInfo =
				new PlayerTankInformation(this.findOrGetUpdateForTank(tankId),
						player);
		em.persist(tankInfo);
		return tankInfo;
	}
}
