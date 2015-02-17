package blood.steel.server.model.character.messaging;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.items.Item;
import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class EquipmentChangeMessage extends GameMessage {

	private String slot;
	private Long id;
	
	public EquipmentChangeMessage() {}
	public EquipmentChangeMessage(Item item, String slot) {
		this.slot = slot;
		id = item.getId();
	}
	
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	
}
