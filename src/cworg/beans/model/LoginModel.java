package cworg.beans.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cworg.data.User;

@ManagedBean
@SessionScoped
public class LoginModel {
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
