package blood.steel.server.model.location.trainingroom.messaging;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.location.messaging.LocationMessage;

@XmlRootElement(name="gameMessage")
public class DuelApplicationChangeMessage extends LocationMessage {

	private String owner;
	private String name;
	
	public DuelApplicationChangeMessage() {	}
	public DuelApplicationChangeMessage(String owner, String characterName) {
		this.owner = owner;
		this.name = characterName;
	}
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
