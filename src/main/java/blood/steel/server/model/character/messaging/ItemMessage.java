package blood.steel.server.model.character.messaging;

import blood.steel.server.model.items.Item;

public class ItemMessage {

	private long id;
	private String name;
	
	public ItemMessage() {}
	public ItemMessage(Item item) {
		this.id = item.getId();
		this.name = item.getName();
	}
	
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
