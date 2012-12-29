package cworg.beans.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import cworg.beans.model.LoginModel;

@ManagedBean
public class LoginController {
	@ManagedProperty(value = "#{loginModel}")
	private LoginModel model;

	public LoginModel getModel() {
		return model;
	}

	public void setModel(LoginModel model) {
		this.model = model;
	}

	public String submit() {
		// TODO
		return null;
	}
}
