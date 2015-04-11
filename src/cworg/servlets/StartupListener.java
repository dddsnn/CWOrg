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
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import cworg.data.FreezeDurations;

public class StartupListener implements ServletContextListener {
	@PersistenceContext
	private EntityManager em;

	@Override
	public void contextDestroyed(ServletContextEvent ev) {
		// nop
	}

	@Override
	@Transactional(TxType.REQUIRED)
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
		if (em.find(FreezeDurations.class, "standard") == null) {
			String name = "standard";
			List<Duration> lightDurations = new ArrayList<>(11);
			List<Duration> mediumDurations = new ArrayList<>(11);
			List<Duration> heavyDurations = new ArrayList<>(11);
			List<Duration> tdDurations = new ArrayList<>(11);
			List<Duration> spgDurations = new ArrayList<>(11);
			// http://worldoftanks.eu/en/content/clanwars_guide/expansion/destroyed-vehicles-blocking/
			lightDurations.add(null);
			lightDurations.add(Duration.ZERO);
			lightDurations.add(Duration.ofHours(1));
			lightDurations.add(Duration.ofHours(2));
			lightDurations.add(Duration.ofHours(4));
			lightDurations.add(Duration.ofHours(16));
			lightDurations.add(Duration.ofHours(16));
			lightDurations.add(Duration.ofHours(16));
			lightDurations.add(Duration.ofHours(48));
			mediumDurations.add(null);
			mediumDurations.add(Duration.ZERO);
			mediumDurations.add(Duration.ofHours(1));
			mediumDurations.add(Duration.ofHours(4));
			mediumDurations.add(Duration.ofHours(16));
			mediumDurations.add(Duration.ofHours(25));
			mediumDurations.add(Duration.ofHours(30));
			mediumDurations.add(Duration.ofHours(48));
			mediumDurations.add(Duration.ofHours(72));
			mediumDurations.add(Duration.ofHours(96));
			mediumDurations.add(Duration.ofHours(120));
			heavyDurations.add(null);
			heavyDurations.add(null);
			heavyDurations.add(null);
			heavyDurations.add(null);
			heavyDurations.add(Duration.ofHours(24));
			heavyDurations.add(Duration.ofHours(30));
			heavyDurations.add(Duration.ofHours(48));
			heavyDurations.add(Duration.ofHours(72));
			heavyDurations.add(Duration.ofHours(96));
			heavyDurations.add(Duration.ofHours(120));
			heavyDurations.add(Duration.ofHours(168));
			tdDurations.add(null);
			tdDurations.add(null);
			tdDurations.add(Duration.ofHours(1));
			tdDurations.add(Duration.ofHours(4));
			tdDurations.add(Duration.ofHours(16));
			tdDurations.add(Duration.ofHours(25));
			tdDurations.add(Duration.ofHours(30));
			tdDurations.add(Duration.ofHours(48));
			tdDurations.add(Duration.ofHours(72));
			tdDurations.add(Duration.ofHours(96));
			tdDurations.add(Duration.ofHours(96));
			spgDurations.add(null);
			spgDurations.add(null);
			spgDurations.add(Duration.ofHours(4));
			spgDurations.add(Duration.ofHours(8));
			spgDurations.add(Duration.ofHours(18));
			spgDurations.add(Duration.ofHours(27));
			spgDurations.add(Duration.ofHours(36));
			spgDurations.add(Duration.ofHours(50));
			spgDurations.add(Duration.ofHours(64));
			spgDurations.add(Duration.ofHours(72));
			spgDurations.add(Duration.ofHours(72));
			FreezeDurations standard =
					new FreezeDurations(name, lightDurations, mediumDurations,
							heavyDurations, tdDurations, spgDurations);
			em.persist(standard);
		}
	}
}
