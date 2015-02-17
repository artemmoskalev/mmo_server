package blood.steel.server.model.location.shop.messaging;

import java.util.*;

import javax.xml.bind.annotation.*;

import blood.steel.server.model.location.messaging.*;

@XmlRootElement(name="gameMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemListMessage extends LocationMessage {

	private List<ItemQuantity> item;

	public ItemListMessage() {
		item = new ArrayList<ItemQuantity>();
	}
	
	public List<ItemQuantity> getItem() {
		return item;
	}
	public void setItem(List<ItemQuantity> item) {
		this.item = item;
	}
	public void addItem(ItemQuantity itemQuantity) {
		item.add(itemQuantity);
	}
	
}
