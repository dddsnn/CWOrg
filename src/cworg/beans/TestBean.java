package cworg.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
@Named("testBean")
public class TestBean {
	@Inject
	private HttpServletRequest request;
	@EJB
	private TestDB db;

	public String getTest() {
		List<TestData> things = db.getAllThings();
		String thingString = "";
		for (TestData thing : things) {
			thingString += thing.getData();
		}
		return request.getMethod() + thingString;
	}

	public void addAThing(String stuff) {
		db.addStuff(stuff);
	}

}
