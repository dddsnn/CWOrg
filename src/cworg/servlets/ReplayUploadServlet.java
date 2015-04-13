package cworg.servlets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cworg.data.ReplayBattle.BattleType;
import cworg.db.DBAccess;
import cworg.replay.ParseReplayResponse;
import cworg.replay.ReplayException;
import cworg.replay.ReplayExistsException;
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
		try {
			db.createReplayBattle(replayResp);
		} catch (WebException | WgApiError e) {
			throw new ServletException(e);
		} catch (ReplayExistsException e) {
			throw new ServletException(e);
		}

		// redirect home TODO success msg
		response.sendRedirect(request.getContextPath() + "/home/");
	}
}
