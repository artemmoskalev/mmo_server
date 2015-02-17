package blood.steel.server.model.character;

import java.util.List;

import javax.persistence.*;

import blood.steel.server.model.items.*;

@Embeddable
public class Inventory {
	
	private int totalInventorySize;
	private int currentInventorySize;
	
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn
	protected List<Item> inventory;
	
	public Inventory() { }
	public Inventory(int totalSize) {
		this.totalInventorySize = totalSize;
	}
	
	public int getTotalInventorySize() {
		return totalInventorySize;
	}
	public void setTotalInventorySize(int totalInventorySize) {
		this.totalInventorySize = totalInventorySize;
	}
	public int getCurrentInventorySize() {
		return currentInventorySize;
	}
	public void setCurrentInventorySize(int currentInventorySize) {
		this.currentInventorySize = currentInventorySize;
	}
	
	public boolean addItem(Item item) {
		if(totalInventorySize >= item.getWeight() + currentInventorySize) {
			inventory.add(item);
			currentInventorySize += item.getWeight();
			return true;
		} else {
			return false;
		}
	}
	public boolean removeItem(Item item) {
		if(inventory.remove(item)) {
			currentInventorySize = currentInventorySize-item.getWeight();
			return true;
		} else {
			return false;
		}	
	}
	public Item getItemById(long id) {
		Item article = null;
		for(Item item : inventory) {
			if(item.getId() == id) {
				article = item;
				break;
			}
		}
		return article;
	}
	public List<Item> getItemList() {
		return inventory;
	}
	
}
