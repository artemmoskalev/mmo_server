package blood.steel.server.model.util.messaging;

import java.util.*;

import blood.steel.server.model.character.messaging.EquipmentChangeMessage;
import blood.steel.server.model.character.messaging.InventoryChangeMessage;
import blood.steel.server.model.character.messaging.InventoryMessage;
import blood.steel.server.model.items.Item;
import blood.steel.server.model.messaging.*;

public class CharacterMessageFactory {

	private static CharacterMessageFactory factory = new CharacterMessageFactory();
	
	private CharacterMessageFactory() { };
		
	public static CharacterMessageFactory getCharacterMessageFactory() {
		return factory;
	}
	
	/**
	 * Creates message stating that this character has no such command
	 */
	public GameMessage createCharacterNoCommandMessage(String request) {
		InformationMessage message = new InformationMessage(request);
		message.setCode(2000);
		return message;
	}
	/**
	 * Creates a message stating that the inventory is full and no item can be added
	 */
	public GameMessage createInventoryFullMessage() {
		InformationMessage message = new InformationMessage();
		message.setCode(2010);
		message.setText("Inventory is full!");
		return message;
	}
	/**
	 * Creates a message stating that the item has been added to the inventory
	 */
	public GameMessage createItemReceivedMessage(Item article) {
		InventoryChangeMessage message = new InventoryChangeMessage();
		message.setCode(2011);
		message.setId(article.getId());
		message.setName(article.getName());
		return message;
	}
	/**
	 * Creates a message stating that the item has been removed from the inventory
	 */
	public GameMessage createItemRemovedMessage(Item article) {
		InventoryChangeMessage message = new InventoryChangeMessage();
		message.setCode(2012);
		message.setId(article.getId());
		message.setName(article.getName());
		return message;
	}
	/**
	 * Creates a message stating that the the item does not exist in the inventory to remove
	 */
	public GameMessage createItemRemovedFailureMessage(Item article) {
		InformationMessage message = new InformationMessage();
		message.setText("Item to remove does not exist!");
		message.setCode(2013);
		return message;
	}
	/**
	 * Creates a message which lists all items in the inventory
	 */
	public GameMessage createInventoryItemListMessage(List<Item> items) {
		InventoryMessage message = new InventoryMessage();
		message.setCode(2014);
		for(Item item : items) {
			message.addItem(item);
		}
		return message;
	}
	/**
	 * Creates a message stating that a particular item has been equipped into one of the weapon slots
	 */
	public GameMessage createItemEquippedMessage(Item item, String slot) {
		EquipmentChangeMessage message = new EquipmentChangeMessage(item, slot);
		message.setCode(2015);
		return message;
	}
	/**
	 * Creates a message stating that one item has been removed from the equipment slot
	 */
	public GameMessage createItemUnequippedMessage(Item item, String slot) {
		EquipmentChangeMessage message = new EquipmentChangeMessage(item, slot);
		message.setCode(2016);
		return message;
	}
	/**
	 * Creates a message stating that the item could not be equipped due to the fact that the slot is taken by other item
	 */
	public GameMessage createItemEquippedFailureMessage(String slot) {
		EquipmentChangeMessage message = new EquipmentChangeMessage();
		message.setCode(2017);
		message.setSlot(slot);
		return message;
	}
	/**
	 * Creates a message stating that the item could not be used because hero stats are not enough
	 */
	public GameMessage createItemCannotBeUsedMessage(Item item) {
		InventoryChangeMessage message = new InventoryChangeMessage();
		message.setCode(2018);
		message.setId(item.getId());
		message.setName(item.getName());
		return message;
	}
	
}
