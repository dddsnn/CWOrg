package cworg.beans;



import java.util.List;

import javax.ejb.Remote;

@Remote
public interface TestDB {
	List<TestData> getAllThings();
	void addStuff(String stuff);
}
