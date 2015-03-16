package cworg.servlets;

import java.io.IOException;
import java.time.Duration;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cworg.data.LoginInfo;
import cworg.data.Player;
import cworg.data.User;
import cworg.web.ProlongateResponse;
import cworg.web.WebException;
import cworg.web.WgAccess;
import cworg.web.WgApiError;

@WebServlet("/login/callback/")
public class LoginCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private WgAccess wg;

	public LoginCallbackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	protected void handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String status = req.getParameter("status");
		if ("ok".equals(status)) {
			// parse data
			String token = req.getParameter("access_token");
			String nick = req.getParameter("nickname");
			String accountId = req.getParameter("account_id");
			String expiryTimeStr = req.getParameter("expires_at");
			if (token == null || nick == null || accountId == null
					|| expiryTimeStr == null) {
				// TODO invalid response from wg, send to error page
				return;
			}

			// prolongate access token and confirm it's legit
			ProlongateResponse prlResp = null;
			try {
				// TODO longer duration
				prlResp = wg.prolongate(token, Duration.ofHours(1));
			} catch (WebException e) {
				// TODO error page
			} catch (WgApiError e) {
				// TODO error page
			}
			if (!accountId.equals(prlResp.getAccountId())) {
				// TODO error page (forged token)
				return;
			}

			// store data in session
			// TODO check if user is already in db
			LoginInfo loginInfo =
					new LoginInfo(prlResp.getAccessToken(),
							prlResp.getExpiryTime());
			// TODO load player if new
			User user = new User(prlResp.getAccountId(), new Player());
			user.setLoginInfo(loginInfo);
			req.getSession().setAttribute("user", user);
			resp.sendRedirect(req.getContextPath() + "/");
		} else if ("error".equals(status)) {
			// TODO
		} else if (status == null) {
			// no status, just redirect to index
			resp.sendRedirect(req.getContextPath() + "/");
		} else {
			// TODO invalid status
		}
	}
}
