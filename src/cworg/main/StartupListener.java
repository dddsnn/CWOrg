package cworg.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Puts the app id into the ServletContext.
 */
public class StartupListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// nop
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		InputStream is =
				getClass().getClassLoader().getResourceAsStream(
						"/app-id.properties");
		if (is == null) {
			System.err.println("Couldn't find app-id.properties");
			e.getServletContext().setAttribute("app-id", "");
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
		e.getServletContext().setAttribute("app-id", appId);
	}

}
