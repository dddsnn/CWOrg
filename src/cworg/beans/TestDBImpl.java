package cworg.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton
public class TestDBImpl implements TestDB {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<TestData> getAllThings() {
		Query q = em.createNamedQuery("all");
		List<TestData> l = q.getResultList();
		return l;
	}

	@Override
	public void addStuff(String stuff) {
		TestData thing = new TestData(stuff);
		em.persist(thing);
		
	}

}
