package cworg.db;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.FreezeDurations;
import cworg.data.Player;
import cworg.data.PlayerTankInformation;
import cworg.data.ReplayBattle;
import cworg.data.ReplayPlayer;
import cworg.data.Tank;
import cworg.data.TankFreezeInformation;
import cworg.data.User;
import cworg.replay.ParseReplayResponse;
import cworg.replay.ParseReplayResponse.ParseReplayResponsePlayer;
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
	public ReplayBattle createReplayBattle(ParseReplayResponse replayResp)
			throws WebException, WgApiError {
		ReplayBattle replay =
				em.find(ReplayBattle.class, new ReplayBattle.ReplayBattlePK(
						replayResp.getArenaId(), replayResp.getPlayerId()));
		if (replay != null) {
			// TODO replay of this battle from this player has already been
			// uploaded
			// throw new ServletException("already been upped");
			return replay;
		}

		Player recordingPlayer = null;
		Set<ReplayPlayer> team1 = null;
		Set<ReplayPlayer> team2 = null;
		recordingPlayer = this.findOrCreatePlayer(replayResp.getPlayerId());
		team1 = this.makeTeam(replayResp.getTeam1());
		team2 = this.makeTeam(replayResp.getTeam2());
		replay =
				new ReplayBattle(replayResp.getArenaId(), recordingPlayer,
						replayResp.getBattleType(),
						replayResp.isLockingEnabled(),
						replayResp.getArenaCreateTime(),
						replayResp.getMapName(), replayResp.getDuration(),
						replayResp.getWinningTeam(), replayResp.getOutcome(),
						team1, team2);
		setBattleOnPlayers(replay, team1);
		setBattleOnPlayers(replay, team2);
		em.persist(replay);
		this.makeFreezeInfos(replay, team1);
		this.makeFreezeInfos(replay, team2);
		return replay;
	}

	private void makeFreezeInfos(ReplayBattle replay, Set<ReplayPlayer> players) {
		// TODO conditional on this being a cw, probably factor out
		Clan ownerClan = replay.getPlayer().getClanInfo().getClan();
		// battle end time: 45 seconds extra for battle loading
		Instant endTime =
				replay.getArenaCreateTime().plus(replay.getDuration())
						.plus(Duration.ofSeconds(45));
		// TODO decide which to use based on user input, freeze duration of
		// uploading player or something
		FreezeDurations freezeDurations =
				em.find(FreezeDurations.class, "standard");
		for (ReplayPlayer p : players) {
			if (p.isSurvived() || p.getTank() == null) {
				// not interested if tank survived (not frozen), or wasn't
				// spotted in fog of war
				continue;
			}
			Query q =
					em.createNamedQuery("findPlayerTank",
							PlayerTankInformation.class);
			q.setParameter("player", p.getPlayer());
			q.setParameter("tank", p.getTank());
			PlayerTankInformation tankInfo =
					(PlayerTankInformation) q.getSingleResult();
			TankFreezeInformation freezeInfo =
					new TankFreezeInformation(tankInfo, ownerClan);
			Instant unfreezeTime = endTime;
			Tank tank = p.getTank();
			switch (tank.getInternalType()) {
			case "heavyTank":
				unfreezeTime =
						unfreezeTime.plus(freezeDurations.getHeavyDurations()
								.get(tank.getTier()));
				break;
			case "mediumTank":
				unfreezeTime =
						unfreezeTime.plus(freezeDurations.getMediumDurations()
								.get(tank.getTier()));
				break;
			case "lightTank":
				unfreezeTime =
						unfreezeTime.plus(freezeDurations.getLightDurations()
								.get(tank.getTier()));
				break;
			case "AT-SPG":
				unfreezeTime =
						unfreezeTime.plus(freezeDurations.getTdDurations().get(
								tank.getTier()));
				break;
			case "SPG":
				unfreezeTime =
						unfreezeTime.plus(freezeDurations.getSpgDurations()
								.get(tank.getTier()));
				break;
			}
			if (unfreezeTime.isBefore(Instant.now())) {
				// irrelevant
				return;
			}
			freezeInfo.setUnfreezeTime(unfreezeTime);
			ownerClan.getFreezeInfos().add(freezeInfo);
		}
	}

	private Set<ReplayPlayer> makeTeam(Map<Long, ParseReplayResponsePlayer> team)
			throws WebException, WgApiError {
		Set<ReplayPlayer> res = new HashSet<>();
		for (Map.Entry<Long, ParseReplayResponsePlayer> e : team.entrySet()) {
			long id = e.getKey();
			ParseReplayResponsePlayer responsePlayer = e.getValue();
			ReplayPlayer replayPlayer =
					new ReplayPlayer(responsePlayer.isSurvived());
			replayPlayer.setPlayer(this.findOrCreatePlayer(id));
			if (responsePlayer.getTankId() != 0) {
				replayPlayer.setTank(this.findOrGetUpdateForTank(responsePlayer
						.getTankId()));
			} else {
				// fog of war
				replayPlayer.setTank(null);
			}
			res.add(replayPlayer);
		}
		return res;
	}

	private static void setBattleOnPlayers(ReplayBattle battle,
			Set<ReplayPlayer> players) {
		for (ReplayPlayer p : players) {
			p.setBattle(battle);
		}
	}

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
				player.getTankInfos().add(
						this.createPlayerTankInfo(player, tankId));
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
