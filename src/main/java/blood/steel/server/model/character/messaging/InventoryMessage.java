package blood.steel.server.model.character.messaging;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.items.Item;
import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class InventoryMessage extends GameMessage {

	private List<ItemMessage> item = new ArrayList<ItemMessage>();

	public List<ItemMessage> getItem() {
		return item;
	}
	public void setItem(List<ItemMessage> item) {
		this.item = item;
	}
	public void addItem(Item newItem) {
		item.add(new ItemMessage(newItem));
	}
	
}
