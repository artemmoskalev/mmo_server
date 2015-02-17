package blood.steel.server.model.util;

import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class WeaponParser extends DefaultHandler {

	private Map<String, Map<String, String>> weaponProperties = new HashMap<String, Map<String, String>>();
		
	public Map<String, Map<String, String>> getWeaponProperties() {
		return weaponProperties;
	}
	
	private String currentTag = "";
		
	private Map<String, String> currentWeaponProperties = null;
	private boolean inRequirements = false;
	private boolean inProperties = false;
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(inRequirements) {
			if(currentTag.equals("level")) {
				currentWeaponProperties.put("levelRequirement", new String(ch, start, length));
			} else if(currentTag.equals("strength")) {
				currentWeaponProperties.put("strengthRequirement", new String(ch, start, length));
			} else if(currentTag.equals("agility")) {
				currentWeaponProperties.put("agilityRequirement", new String(ch, start, length));
			} else if(currentTag.equals("luck")) {
				currentWeaponProperties.put("luckRequirement", new String(ch, start, length));
			} else if(currentTag.equals("toughness")) {
				currentWeaponProperties.put("toughnessRequirement", new String(ch, start, length));
			}
		}
		if(inProperties) {
			if(currentTag.equals("damage")) {
				currentWeaponProperties.put("damage", new String(ch, start, length));
			} else if(currentTag.equals("hp")) {
				currentWeaponProperties.put("hp", new String(ch, start, length));
			} else if(currentTag.equals("strength")) {
				currentWeaponProperties.put("strength", new String(ch, start, length));
			} else if(currentTag.equals("agility")) {
				currentWeaponProperties.put("agility", new String(ch, start, length));
			} else if(currentTag.equals("luck")) {
				currentWeaponProperties.put("luck", new String(ch, start, length));
			} else if(currentTag.equals("toughness")) {
				currentWeaponProperties.put("toughness", new String(ch, start, length));
			} else if(currentTag.equals("critical")) {
				currentWeaponProperties.put("critical", new String(ch, start, length));
			} else if(currentTag.equals("antiCritical")) {
				currentWeaponProperties.put("antiCritical", new String(ch, start, length));
			} else if(currentTag.equals("dodge")) {
				currentWeaponProperties.put("dodge", new String(ch, start, length));
			} else if(currentTag.equals("antiDodge")) {
				currentWeaponProperties.put("antiDodge", new String(ch, start, length));
			} else if(currentTag.equals("headArmor")) {
				currentWeaponProperties.put("headArmor", new String(ch, start, length));
			} else if(currentTag.equals("chestArmor")) {
				currentWeaponProperties.put("chestArmor", new String(ch, start, length));
			} else if(currentTag.equals("abdomenArmor")) {
				currentWeaponProperties.put("abdomenArmor", new String(ch, start, length));
			} else if(currentTag.equals("legArmor")) {
				currentWeaponProperties.put("legArmor", new String(ch, start, length));
			}			
		}		
		if(currentTag.equals("name")) {
			currentWeaponProperties.put("name", new String(ch, start, length));
		} else if(currentTag.equals("price")) {
			currentWeaponProperties.put("price", new String(ch, start, length));
		} else if(currentTag.equals("weight")) {
			currentWeaponProperties.put("weight", new String(ch, start, length));
		} else if(currentTag.equals("description")) {
			currentWeaponProperties.put("description", new String(ch, start, length));
		} 	
	}
	
	@Override
	public void startElement(java.lang.String uri, java.lang.String localName,
							 java.lang.String qName, Attributes attributes) throws SAXException {
		currentTag = qName;
		if(qName.equals("weapon")) {
			currentWeaponProperties = new HashMap<String, String>(); 
			currentWeaponProperties.put("type", attributes.getValue("type"));
		} else if(qName.equals("requirements")) {			
			inRequirements = true;
		} else if(qName.equals("properties")) {
			inProperties = true;
		}
	}
	@Override 
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException { 
		if(qName.equals("weapon")) {			
			weaponProperties.put(currentWeaponProperties.get("name"), currentWeaponProperties);
		}
		if(qName.equals("requirements")) {
			inRequirements = false;
		} else if(qName.equals("properties")) {
			inProperties = false;
		}		
		currentTag = "";	
	} 
	
	@Override
	public void startDocument() throws SAXException {
		System.out.println("Parsing weapon list...");
	}
	@Override
	public void endDocument() throws SAXException {
		System.out.println("Success!");
	}
	
}
