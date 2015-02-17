package blood.steel.server.model.util;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;

import blood.steel.server.model.items.*;
import blood.steel.server.model.items.weapons.*;

public class WeaponFactory {
		
	private static Map<String, Map<String, String>> weaponProperties;
	
	static {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			WeaponParser handler = new WeaponParser();
			parser.parse(new File("weapons.xml"), handler);
			weaponProperties = handler.getWeaponProperties();
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		} catch (SAXException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
	
	public static Item createWeapon(String name) {
		Weaponry weaponry = null;
		Map<String, String> properties = weaponProperties.get(name);
		String type = properties.get("type");
		if(type.equals("weapon")) {
			weaponry = new Weapon();
		} else if(type.equals("shield")) {
			weaponry = new Shield();
		} else if(type.equals("helmet")) {
			weaponry = new Shield();
		} else if(type.equals("mail")) {
			weaponry = new Shield();
		} else if(type.equals("belt")) {
			weaponry = new Shield();
		} else if(type.equals("boots")) {
			weaponry = new Shield();
		}
		weaponry.setId(ItemSequenceGenerator.getNewItemId());
		weaponry.setRequirements(new Requirements());
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			if(entry.getKey().equals("name")) {
				weaponry.setName(entry.getValue());
			} else if(entry.getKey().equals("price")) {
				weaponry.setBasicPrice(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("weight")) {
				weaponry.setWeight(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("description")) {
				weaponry.setDescription(entry.getValue());
			} else if(entry.getKey().equals("levelRequirement")) {
				weaponry.getRequirements().setLevelRequirement(Integer.parseInt(entry.getValue()));
			} else if(entry.getKey().equals("strengthRequirement")) {
				weaponry.getRequirements().setStrengthRequirement(Integer.parseInt(entry.getValue()));
			} else if(entry.getKey().equals("agilityRequirement")) {
				weaponry.getRequirements().setAgilityRequirement(Integer.parseInt(entry.getValue()));
			} else if(entry.getKey().equals("luckRequirement")) {
				weaponry.getRequirements().setLuckRequirement(Integer.parseInt(entry.getValue()));
			} else if(entry.getKey().equals("toughnessRequirement")) {
				weaponry.getRequirements().setToughnessRequirement(Integer.parseInt(entry.getValue()));
			} else if(entry.getKey().equals("damage")) {
				weaponry.setDamage(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("hp")) {
				weaponry.setHp(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("strength")) {
				weaponry.setStrength(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("agility")) {
				weaponry.setAgility(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("luck")) {
				weaponry.setLuck(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("toughness")) {
				weaponry.setToughness(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("critical")) {
				weaponry.setCritical(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("antiCritical")) {
				weaponry.setAntiCritical(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("dodge")) {
				weaponry.setDodge(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("antiDodge")) {
				weaponry.setAntiDodge(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("headArmor")) {
				weaponry.setHeadArmor(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("chestArmor")) {
				weaponry.setChestArmor(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("abdomenArmor")) {
				weaponry.setAbdomenArmor(Integer.valueOf(entry.getValue()));
			} else if(entry.getKey().equals("legArmor")) {
				weaponry.setLegArmor(Integer.valueOf(entry.getValue()));
			}	
		}
		return weaponry;		
	}
	
	public static String getWeaponProperty(String weaponName, String property) {
		Map<String, String> properties = weaponProperties.get(weaponName);
		return properties.get(property);
	}
	
	public static List<String> listItemNames() {
		List<String> items = new ArrayList<String>();
		for (String key : weaponProperties.keySet()) {
			items.add(key);
		}
		return items;
	}
	
}
