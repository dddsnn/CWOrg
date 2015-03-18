package cworg.data;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
// user is reserved keyword in derby
@Table(name="USER_TABLE")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ACCOUNT_ID")
	private String accountId;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "ACCOUNT_ID")
	private Player player;
	@Transient
	private LoginInfo loginInfo;

	public User() {
	}

	public User(String accountId, Player player) {
		this.accountId = accountId;
		this.player = player;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public String getAccountId() {
		return accountId;
	}

	public Player getPlayer() {
		return player;
	}
}
