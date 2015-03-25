package cworg.servlets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import cworg.replay.ParseReplayResponse;
import cworg.replay.ReplayException;
import cworg.replay.ReplayImport;

@WebServlet("/replayupload/")
@MultipartConfig
public class ReplayUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private ReplayImport replayImport;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO
		ParseReplayResponse replayResponse = null;
		for (Part part : request.getParts()) {
			try (InputStream is = part.getInputStream()) {
				replayResponse =
						replayImport.parseReplay(new BufferedInputStream(is));
			} catch (IOException | ReplayException e) {
				throw new ServletException(e);
			}
		}
		request.setAttribute("replay", replayResponse);
		getServletContext().getRequestDispatcher("/WEB-INF/jsf/replay.xhtml")
				.forward(request, response);
	}
}
