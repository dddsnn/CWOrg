package cworg.servlets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cworg.data.Clan;
import cworg.data.Player;
import cworg.data.PlayerTankInformation;
import cworg.data.ReplayBattle;
import cworg.data.ReplayBattle.BattleType;
import cworg.data.ReplayPlayer;
import cworg.data.TankFreezeInformation;
import cworg.db.DBAccess;
import cworg.replay.ParseReplayResponse;
import cworg.replay.ParseReplayResponse.ParseReplayResponsePlayer;
import cworg.replay.ReplayException;
import cworg.replay.ReplayImport;
import cworg.web.WebException;
import cworg.web.WgApiError;

@WebServlet("/home/replayupload/")
@MultipartConfig
public class ReplayUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private ReplayImport replayImport;
	@EJB
	private DBAccess db;
	@PersistenceContext
	private EntityManager em;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO make sure only one file can be uploaded (in jsf), or support
		// multiples
		ParseReplayResponse replayResp = null;
		Iterator<Part> it = request.getParts().iterator();
		if (!it.hasNext()) {
			// TODO nothing was uploaded
			return;
		}
		Part part = it.next();
		try (InputStream is = part.getInputStream()) {
			replayResp = replayImport.parseReplay(new BufferedInputStream(is));
		} catch (IOException | ReplayException e) {
			throw new ServletException(e);
		}
		// TODO do useful stuff with other battle types (?)
		if (replayResp.getBattleType() != BattleType.CW) {
			// TODO anything other than clan wars doesn't interest us
			throw new ServletException("no CW");
		}
		ReplayBattle replay =
				em.find(ReplayBattle.class, new ReplayBattle.ReplayBattlePK(
						replayResp.getArenaId(), replayResp.getPlayerId()));
		if (replay != null) {
			// TODO replay of this battle from this player has already been
			// uploaded
			throw new ServletException("already been upped");
		}

		Player recordingPlayer = null;
		Set<ReplayPlayer> team1 = null;
		Set<ReplayPlayer> team2 = null;
		try {
			recordingPlayer = db.findOrCreatePlayer(replayResp.getPlayerId());
			team1 = this.makeTeam(replayResp.getTeam1());
			team2 = this.makeTeam(replayResp.getTeam2());
		} catch (WebException e) {
			throw new ServletException(e);
		} catch (WgApiError e) {
			throw new ServletException(e);
		}
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
		// make freeze infos out of the battle
		// TODO conditional on this being a cw, probably factor out
		Clan ownerClan = replay.getPlayer().getClanInfo().getClan();
		for (ReplayPlayer p : team1) {
			if (p.isSurvived()) {
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
			// TODO set unfreeze time
			ownerClan.getFreezeInfos().add(freezeInfo);
		}
		// redirect home TODO success msg
		response.sendRedirect(request.getContextPath() + "/home/");
	}

	private Set<ReplayPlayer> makeTeam(Map<Long, ParseReplayResponsePlayer> team)
			throws WebException, WgApiError {
		Set<ReplayPlayer> res = new HashSet<>();
		for (Map.Entry<Long, ParseReplayResponsePlayer> e : team.entrySet()) {
			long id = e.getKey();
			ParseReplayResponsePlayer p = e.getValue();
			ReplayPlayer player = new ReplayPlayer(p.isSurvived());
			player.setPlayer(db.findOrCreatePlayer(id));
			player.setTank(db.findOrGetUpdateForTank(p.getTankId()));
			em.persist(player);
		}
		return res;
	}

	private static void setBattleOnPlayers(ReplayBattle battle,
			Set<ReplayPlayer> players) {
		for (ReplayPlayer p : players) {
			p.setBattle(battle);
		}
	}
}
