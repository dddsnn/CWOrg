package cworg.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.User;
import cworg.web.WgAccess;

@RequestScoped
@Named("clanListBean")
public class ClanListBean {
	@Inject
	private HttpSession session;
	@EJB
	private WgAccess wg;

	public List<String> getPlayerNicks() {
		User user = (User) session.getAttribute("user");
		Clan clan = user.getPlayer().getClanInfo().getClan();
		List<String> res = new ArrayList<>();
		for (ClanMemberInformation cm : clan.getMembers()) {
			Player p = cm.getPlayer();
			String playerName = p.getNick();
			res.add(playerName);
		}
		return res;
	}
}
