package cworg.db;

import javax.ejb.Local;

import cworg.data.User;

@Local
public interface DBAccess {
	public User findOrCreateUser(String accountId);
}
