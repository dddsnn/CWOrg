package cworg.beans;

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
@Named("loginBean")
public class LoginBean {
	@Inject
	private HttpSession session;
	@EJB
	private WgAccess wg;

	public boolean isLoggedIn() {
		return session.getAttribute("user") != null;
	}

	public String getAccountId() {
		LoggedInUser user = (LoggedInUser) session.getAttribute("user");
		return user == null ? "" : user.getAccountId();
	}

	public String logout() {
		LoggedInUser user = (LoggedInUser) session.getAttribute("user");
		if (user == null) {
			// not logged in
			return "notlog";
		}
		try {
			wg.logout(user.getAccessToken());
		} catch (WebException e) {
			return "webex";
		} catch (WgApiError e) {
			return "apier";
		}
		// TODO maybe remove from session on exception?
		// successfully invalidated the token, remove user from session
		session.removeAttribute("user");
		return null;
	}
}
