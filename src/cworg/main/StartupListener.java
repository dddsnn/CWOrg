package cworg.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartupListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent ev) {
		// nop
	}

	@Override
	public void contextInitialized(ServletContextEvent ev) {
		// put app id into servletcontext
		InputStream is =
				getClass().getClassLoader().getResourceAsStream(
						"/app-id.properties");
		if (is == null) {
			System.err.println("Couldn't find app-id.properties");
			ev.getServletContext().setAttribute("app-id", "");
			return;
		}
		Properties prop = new Properties();
		try {
			prop.load(is);
			is.close();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		String appId = (String) prop.get("app-id");
		ev.getServletContext().setAttribute("app-id", appId);
	}
}
