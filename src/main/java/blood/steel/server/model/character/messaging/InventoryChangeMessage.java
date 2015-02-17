package blood.steel.server.model.character.messaging;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class InventoryChangeMessage extends GameMessage {

	private long id;
	private String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
