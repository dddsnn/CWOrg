package cworg.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Singleton, will translate e.g. tank ids from the db to enums
 */
public class DBHelper {
	private static DBHelper instance;
	static {
		try {
			instance = new DBHelper();
		} catch (Exception e) {
		}
	}

	private Properties prop;

	private DBHelper() throws IOException {
		prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream("db.properties");
		prop.load(stream);
	}

	public static DBHelper instance() {
		return instance;
	}

	public Connection getConnection() throws SQLException {
		Connection con =
				DriverManager.getConnection(prop.getProperty("db.url"),
						prop.getProperty("db.user"),
						prop.getProperty("db.password"));
		return con;
	}
}
