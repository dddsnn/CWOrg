package cworg.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import cworg.data.LoggedInUser;
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
		LoggedInUser user = (LoggedInUser) session.getAttribute("user");
		if (user == null) {
			return null;
		}
		try {
			return wg.getVehiclesInGarage(user.getAccountId(),
					user.getAccessToken());
		} catch (WebException | WgApiError e) {
			return null;
		}
	}
}
