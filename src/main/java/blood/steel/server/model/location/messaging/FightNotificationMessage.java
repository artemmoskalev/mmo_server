package blood.steel.server.model.location.messaging;

import javax.xml.bind.annotation.*;

import blood.steel.server.model.fight.Fight;

@XmlRootElement(name="gameMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class FightNotificationMessage extends LocationMessage {

	private long id;
	private int timeout;
	private TeamMessage red;
	private TeamMessage blue;
	
	public FightNotificationMessage() {		}
	public FightNotificationMessage(Fight fight) {
		this.id = fight.getId();
		this.timeout = fight.getTimeout();
		this.red = new TeamMessage(fight.getRedNames());
		this.blue = new TeamMessage(fight.getBlueNames());
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public TeamMessage getRed() {
		return red;
	}
	public void setRed(TeamMessage red) {
		this.red = red;
	}
	public TeamMessage getBlue() {
		return blue;
	}
	public void setBlue(TeamMessage blue) {
		this.blue = blue;
	}	
	
}
