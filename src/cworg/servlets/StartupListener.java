package cworg.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cworg.data.FreezeDurations;

public class StartupListener implements ServletContextListener {
	@PersistenceContext
	private EntityManager em;

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

		// add standard freeze durations
		String name = "standard";
		List<Duration> lightDurations = new ArrayList<>(11);
		List<Duration> mediumDurations = new ArrayList<>(11);
		List<Duration> heavyDurations = new ArrayList<>(11);
		List<Duration> tdDurations = new ArrayList<>(11);
		List<Duration> spgDurations = new ArrayList<>(11);
		// http://worldoftanks.eu/en/content/clanwars_guide/expansion/destroyed-vehicles-blocking/
		lightDurations.set(1, Duration.ZERO);
		lightDurations.set(2, Duration.ofHours(1));
		lightDurations.set(3, Duration.ofHours(2));
		lightDurations.set(4, Duration.ofHours(4));
		lightDurations.set(5, Duration.ofHours(16));
		lightDurations.set(6, Duration.ofHours(16));
		lightDurations.set(7, Duration.ofHours(16));
		lightDurations.set(8, Duration.ofHours(48));
		mediumDurations.set(1, Duration.ZERO);
		mediumDurations.set(2, Duration.ofHours(1));
		mediumDurations.set(3, Duration.ofHours(4));
		mediumDurations.set(4, Duration.ofHours(16));
		mediumDurations.set(5, Duration.ofHours(25));
		mediumDurations.set(6, Duration.ofHours(30));
		mediumDurations.set(7, Duration.ofHours(48));
		mediumDurations.set(8, Duration.ofHours(72));
		mediumDurations.set(9, Duration.ofHours(96));
		mediumDurations.set(10, Duration.ofHours(120));
		heavyDurations.set(4, Duration.ofHours(24));
		heavyDurations.set(5, Duration.ofHours(30));
		heavyDurations.set(6, Duration.ofHours(48));
		heavyDurations.set(7, Duration.ofHours(72));
		heavyDurations.set(8, Duration.ofHours(96));
		heavyDurations.set(9, Duration.ofHours(120));
		heavyDurations.set(10, Duration.ofHours(168));
		tdDurations.set(2, Duration.ofHours(1));
		tdDurations.set(3, Duration.ofHours(4));
		tdDurations.set(4, Duration.ofHours(16));
		tdDurations.set(5, Duration.ofHours(25));
		tdDurations.set(6, Duration.ofHours(30));
		tdDurations.set(7, Duration.ofHours(48));
		tdDurations.set(8, Duration.ofHours(72));
		tdDurations.set(9, Duration.ofHours(96));
		tdDurations.set(10, Duration.ofHours(96));
		spgDurations.set(2, Duration.ofHours(4));
		spgDurations.set(3, Duration.ofHours(8));
		spgDurations.set(4, Duration.ofHours(18));
		spgDurations.set(5, Duration.ofHours(27));
		spgDurations.set(6, Duration.ofHours(36));
		spgDurations.set(7, Duration.ofHours(50));
		spgDurations.set(8, Duration.ofHours(64));
		spgDurations.set(9, Duration.ofHours(72));
		spgDurations.set(10, Duration.ofHours(72));
		FreezeDurations standard =
				new FreezeDurations(name, lightDurations, mediumDurations,
						heavyDurations, tdDurations, spgDurations);
		em.persist(standard);
	}
}
