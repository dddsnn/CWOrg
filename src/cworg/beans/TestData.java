package cworg.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "all", query = "SELECT x FROM TestData x") })
public class TestData implements Serializable {
	@GeneratedValue
	@Id
	private int id;
	private String data;

	public String getData() {
		return data;
	}

	public TestData() {
		this.data = "";
	}

	public TestData(String data) {
		this.data = data;
	}
}
