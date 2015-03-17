package cworg.db;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cworg.data.Player;
import cworg.data.User;

@Stateless
public class DBAccessImpl implements DBAccess {
	@Inject
	EntityManager em;

	@Override
	public User findOrCreateUser(String accountId) {
		User user = em.find(User.class, accountId);
		if (user == null) {
			// TODO load player
			user = new User(accountId, new Player());
			// TODO persist new user
		}
		return user;
	}
}
