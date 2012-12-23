package cworg.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import cworg.data.Player;
import cworg.data.Tank;

@ManagedBean
public class Test {
	public String test;

	public String getTest() {
		test = "hjksdfhjksdfjkjksdf";
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
}
