package cworg.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import cworg.data.Clan;
import cworg.data.ClanMemberInformation;
import cworg.data.Player;
import cworg.data.Tank;
import cworg.data.User;
import cworg.web.WebException;
import cworg.web.WgAccess;
import cworg.web.WgApiError;

@RequestScoped
@Named("dataBean")
public class DataBean {
	@Inject
	private HttpSession session;
	@EJB
	private WgAccess wg;

	public List<String> getTanksInGarage() {
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return null;
		}
		try {
			return wg.getVehiclesInGarage(user.getAccountId(), user
					.getLoginInfo().getAccessToken());
		} catch (WebException | WgApiError e) {
			return null;
		}
	}

	public List<Tank> getAllTanks() {
		// TODO was only for testing anyway
		// try {
		// return wg.getAllTankInfo();
		// } catch (WebException | WgApiError e) {
		return null;
		// }
	}

	public Player getPlayer() {
		User user = (User) session.getAttribute("user");
		return user.getPlayer();
	}

	public Clan getClan() {
		User user = (User) session.getAttribute("user");
		ClanMemberInformation clanInfo = user.getPlayer().getClanInfo();
		if (clanInfo == null) {
			return null;
		} else {
			return clanInfo.getClan();
		}
	}
}
