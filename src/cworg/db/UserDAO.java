package cworg.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import cworg.beans.model.User;

public class UserDAO implements GenericDAO<User, Long> {

	@Override
	public Long create(User newObject) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User read(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User object) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User object) throws SQLException {
		// TODO Auto-generated method stub

	}

	public User read(String name, String password) throws SQLException {
		Connection con = DBHelper.instance().getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		User user = null;

		try {
			stmt = con.createStatement();

			String query = "";
			rs = stmt.executeQuery(query);
		} finally {
			if (con != null) {
				con.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}

		// TODO
		return null;
	}
}
