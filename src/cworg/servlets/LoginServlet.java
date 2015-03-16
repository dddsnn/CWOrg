package cworg.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cworg.web.WebException;
import cworg.web.WgAccess;
import cworg.web.WgApiError;

@WebServlet("/login/")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private WgAccess wg;

	public LoginServlet() {
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
		if (req.getSession().getAttribute("user") != null) {
			// TODO user already logged in, redirect back to index/prolongate
		}
		String redirectUrl =
				String.format("https://%s:%s%s/login/callback/",
						req.getServerName(), req.getServerPort(),
						req.getContextPath());
		try {
			resp.sendRedirect(wg.getLoginUrl(redirectUrl));
		} catch (WebException e) {
			// TODO error page
		} catch (WgApiError e) {
			// TODO error page
		}
	}
}
