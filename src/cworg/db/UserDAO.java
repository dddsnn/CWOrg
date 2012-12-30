package cworg.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.mindrot.jbcrypt.BCrypt;

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
			String query =
					"SELECT u.id, u.passwordBCrypt FROM cworg.users u, cworg.players p WHERE u.id = p.id AND p.name='"
							+ name + "'";
			rs = stmt.executeQuery(query);
			if (rs.next()) {
				long id = rs.getLong(1);
				String pwHash = rs.getString(2);
				// check password, if wrong return null
				if (!BCrypt.checkpw(password, pwHash)) {
					return null;
				}
				user = new User();
				user.setId(id);
				user.setName(name);
			}

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
		return user;
	}
}
