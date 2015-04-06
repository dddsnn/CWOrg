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
import cworg.data.User;
import cworg.db.DBAccess;
import cworg.web.ProlongateResponse;
import cworg.web.WebException;
import cworg.web.WgAccess;
import cworg.web.WgApiError;

@WebServlet("/login/callback/")
public class LoginCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private WgAccess wg;
	@EJB
	private DBAccess db;

	public LoginCallbackServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	private void handle(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String status = req.getParameter("status");
		if ("ok".equals(status)) {
			// parse data
			String token = req.getParameter("access_token");
			String nick = req.getParameter("nickname");
			String accountIdString = req.getParameter("account_id");
			String expiryTimeStr = req.getParameter("expires_at");
			if (token == null || nick == null || accountIdString == null
					|| expiryTimeStr == null) {
				// TODO invalid response from wg, send to error page
				return;
			}
			long accountId = Long.parseLong(accountIdString);

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
			if (accountId != prlResp.getAccountId()) {
				// TODO error page (forged token)
				return;
			}

			// store data in session
			User user = null;
			try {
				user = db.findOrCreateUser(accountId);
			} catch (WebException e) {
				// TODO error page
				e.printStackTrace();
			} catch (WgApiError e) {
				// TODO error page
				e.printStackTrace();
			}
			LoginInfo loginInfo =
					new LoginInfo(prlResp.getAccessToken(),
							prlResp.getExpiryTime());
			user.setLoginInfo(loginInfo);
			req.getSession().setAttribute("user", user);
			resp.sendRedirect(req.getContextPath() + "/home/");
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
