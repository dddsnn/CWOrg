package cworg.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@RequestScoped
@Named("loginBean")
public class LoginBean {
	@Inject
	private HttpSession session;

	public boolean isLoggedIn() {
		return session.getAttribute("token") != null;
	}

	public String getNick() {
		String nick = (String) session.getAttribute("nick");
		return nick == null ? "dfgh" : nick;
	}
}
