package blood.steel.server.model.location;

import java.util.*;

import blood.steel.server.model.character.*;
import blood.steel.server.model.items.*;
import blood.steel.server.model.util.*;
import blood.steel.server.model.util.messaging.GlobalMessageFactory;

public class WeaponShop extends Location {
		
	// map that contains the items and their quantity in the shop
	private Map<String, Integer> quantity;
	private Map<Integer, Map<String, List<String>>> items;
	
	public WeaponShop() {
		super(LocationType.WEAPONSHOP);
		quantity = new HashMap<String,Integer>();	
		items = new HashMap<Integer, Map<String, List<String>>>();
		
		for(String itemName : WeaponFactory.listItemNames()) {
			quantity.put(itemName, new Integer((int)(Math.random()*500 + 500)));
			
			int itemLevel = Integer.valueOf(WeaponFactory.getWeaponProperty(itemName, "levelRequirement"));
			String itemType = WeaponFactory.getWeaponProperty(itemName, "type");
			
			Map<String, List<String>> levelMap = items.get(itemLevel);
			if(levelMap == null) {
				levelMap = new HashMap<String, List<String>>();
				items.put(itemLevel, levelMap);
			}
			List<String> typeList = levelMap.get(itemType);
			if(typeList == null) {
				typeList = new ArrayList<String>();
				levelMap.put(itemType, typeList);
			}
			typeList.add(itemName);			
		}
		
		locationCommands.put("location_buy_item", new BuyItem());
		locationCommands.put("location_list_items", new ListItems());
	}
	
	public synchronized void buyItem(CharacterHandler character, String weaponName) {
		int price = Integer.valueOf(WeaponFactory.getWeaponProperty(weaponName, "price"));				
		if(character.getGold() >= price) {
			if(quantity.get(weaponName) > 0) {
				Item item = WeaponFactory.createWeapon(weaponName);
				if(character.addItem(item)) {
					character.setGold(character.getGold()-price);
					quantity.put(weaponName, quantity.get(weaponName)-1);
				} 
			} else {
				character.onReceive(factory.createOutOfStockMessage());
			}
		} else {
			character.onReceive(factory.createNotEnoughGoldMessage());
		}
	}	
	public synchronized void listStock(CharacterHandler character, String type, Integer weaponLevel) {
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		Map<String, List<String>> levelMap = items.get(weaponLevel);
		if (levelMap != null) {
			List<String> typeList = levelMap.get(type);
			if(typeList != null) {				
				for(String weapon : typeList) {
					resultMap.put(weapon, quantity.get(weapon));
				}
			}
		}
		character.onReceive(factory.createItemList(resultMap));
	}
		
	/*
	 * receives the command to buy a new item
	 * */
	class BuyItem implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			if (character.getState() == CharacterState.IDLE) {
				buyItem(character, parameters.get("name"));				
			} else {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
			}
		}		
	}	
	/*
	 * receives the command to list all items of particular level and type
	 * */
	class ListItems implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			if (character.getState() == CharacterState.IDLE) {
				listStock(character, parameters.get("type"), Integer.valueOf(parameters.get("level")));			
			} else {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
			}
		}		
	}
	
}
