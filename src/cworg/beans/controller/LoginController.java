//package cworg.beans.controller;
//
//import java.sql.SQLException;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//
//import cworg.beans.model.User;
//import cworg.beans.model.LoginModel;
//import cworg.data.Player;
//
//@ManagedBean
//public class LoginController {
//	@ManagedProperty(value = "#{loginModel}")
//	private LoginModel model;
//
//	public LoginModel getModel() {
//		return model;
//	}
//
//	public void setModel(LoginModel model) {
//		this.model = model;
//	}
//
//	public String submit() {
////		User user = null;
////		try {
////			user = new UserDAO().read(model.getUserName(), model.getPassword());
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		// no valid data -> stay on login
////		if (user == null || user.getId() == Player.INVALID_ID)
////			return null;
////		// forward to main
//		return "main.jsf";
//	}
//}
