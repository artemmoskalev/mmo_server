package blood.steel.server.authentication;

import javax.persistence.*;

@Entity
@Table(name="PLAYER")
public class Player {

	@Id
	private String login;
	private String password;
	private String email;
	@Transient
	private PlayerState state = PlayerState.UNKNOWN;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public PlayerState getState() {
		return state;
	}
	public void setState(PlayerState state) {
		this.state = state;
	}
	
}
