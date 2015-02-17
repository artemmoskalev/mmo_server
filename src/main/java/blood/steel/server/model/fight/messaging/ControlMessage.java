package blood.steel.server.model.fight.messaging;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class ControlMessage extends GameMessage {

	private String command;
	private String detail;
	
	public ControlMessage() {
		setCode(1004);
	}

	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
