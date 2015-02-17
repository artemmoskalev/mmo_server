package blood.steel.server.model.location.trainingroom.messaging;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import blood.steel.server.model.location.application.*;
import blood.steel.server.model.location.messaging.TeamMessage;

@XmlAccessorType(XmlAccessType.FIELD)
public class DuelMessage {

	private String owner;
	private int timeout;
	private TeamMessage red;
	private TeamMessage blue;
	
	public DuelMessage() {	}
	public DuelMessage(Application application) {
		this.owner = application.getOwner().getName();
		this.timeout = application.getTimeout();
		this.red = new TeamMessage(application.getRedNames());
		this.blue = new TeamMessage(application.getBlueNames());
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
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}	
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
}
