package blood.steel.server.model.character.messaging;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.character.*;
import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class CharacterMessage extends GameMessage {
	
	private String name;
	private String race;
	private String gender;
	
	private Integer level;
	private Integer experience;
	private Integer minDamage;
	private Integer maxDamage;	
	private Integer hp;
	private Integer currentHp;
	
	private Integer strength;
	private Integer agility;
	private Integer luck;
	private Integer toughness;
	
	private Integer critical;
	private Integer antiCritical;
	private Integer dodge;
	private Integer antiDodge;	
	
	private Integer headArmor;
	private Integer chestArmor;
	private Integer abdomenArmor;
	private Integer legArmor;	
	
	private Integer gold;
	private String place;
	private String onlineState;
	private String state;
	private String image;
	
	private Integer inventoryMax;
	private Integer inventoryCurrent;
	
	private ItemMessage weapon;
	private ItemMessage shield;
	private ItemMessage helmet;
	private ItemMessage mail;
	private ItemMessage belt;
	private ItemMessage boots;
	
	public CharacterMessage() {		}
	
	public CharacterMessage(CharacterHandler character) {
		
		this.setName(character.getName());
		this.setRace(character.getRace().toString());
		this.setGender(character.getGender().toString());
		
		this.setLevel(character.getLevel());
		this.setExperience(character.getExperience());
		this.setMinDamage(character.getMinDamage());
		this.setMaxDamage(character.getMaxDamage());
		this.setHp(character.getHp());
		this.setCurrentHp(character.getCurrentHp());
		
		this.setStrength(character.getStrength());
		this.setAgility(character.getAgility());
		this.setLuck(character.getLuck());
		this.setToughness(character.getToughness());
		
		this.setCritical(character.getCritical());
		this.setAntiCritical(character.getAntiCritical());
		this.setDodge(character.getDodge());
		this.setAntiDodge(character.getAntiDodge());
		
		this.setHeadArmor(character.getHeadArmor());
		this.setChestArmor(character.getChestArmor());
		this.setAbdomenArmor(character.getAbdomenArmor());
		this.setLegArmor(character.getLegArmor());		
		
		this.setGold(character.getGold());
		this.setPlace(character.getPlace().toString());
		this.setOnlineState(character.isOnline());
		this.setState(character.getState().toString());
		this.setImage(character.getImage());
		
		this.setInventoryMax(character.getInventoryMaxSize());
		this.setInventoryCurrent(character.getInventoryCurrentSize());
		
		if(character.getEquipment().getWeapon() != null) {
			this.setWeapon(new ItemMessage(character.getEquipment().getWeapon()));
		}
		if(character.getEquipment().getShield() != null) {
			this.setShield(new ItemMessage(character.getEquipment().getShield()));
		}
		if(character.getEquipment().getHelmet() != null) {
			this.setHelmet(new ItemMessage(character.getEquipment().getHelmet()));
		}
		if(character.getEquipment().getMail() != null) {
			this.setMail(new ItemMessage(character.getEquipment().getMail()));
		}
		if(character.getEquipment().getBelt() != null) {
			this.setBelt(new ItemMessage(character.getEquipment().getBelt()));
		}
		if(character.getEquipment().getBoots() != null) {
			this.setBoots(new ItemMessage(character.getEquipment().getBoots()));		
		}
		
	}
	public CharacterMessage(String name) {
		this.setName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public Integer getMinDamage() {
		return minDamage;
	}
	public void setMinDamage(Integer minDamage) {
		this.minDamage = minDamage;
	}
	public Integer getMaxDamage() {
		return maxDamage;
	}
	public void setMaxDamage(Integer maxDamage) {
		this.maxDamage = maxDamage;
	}
	public Integer getHp() {
		return hp;
	}
	public void setHp(Integer hp) {
		this.hp = hp;
	}
	public Integer getCurrentHp() {
		return currentHp;
	}
	public void setCurrentHp(Integer currentHp) {
		this.currentHp = currentHp;
	}
	public Integer getStrength() {
		return strength;
	}
	public void setStrength(Integer strength) {
		this.strength = strength;
	}
	public Integer getAgility() {
		return agility;
	}
	public void setAgility(Integer agility) {
		this.agility = agility;
	}
	public Integer getLuck() {
		return luck;
	}
	public void setLuck(Integer luck) {
		this.luck = luck;
	}
	public Integer getToughness() {
		return toughness;
	}
	public void setToughness(Integer toughness) {
		this.toughness = toughness;
	}
	public Integer getCritical() {
		return critical;
	}
	public void setCritical(Integer critical) {
		this.critical = critical;
	}
	public Integer getAntiCritical() {
		return antiCritical;
	}
	public void setAntiCritical(Integer antiCritical) {
		this.antiCritical = antiCritical;
	}
	public Integer getDodge() {
		return dodge;
	}
	public void setDodge(Integer dodge) {
		this.dodge = dodge;
	}
	public Integer getAntiDodge() {
		return antiDodge;
	}
	public void setAntiDodge(Integer antiDodge) {
		this.antiDodge = antiDodge;
	}
	public Integer getHeadArmor() {
		return headArmor;
	}
	public void setHeadArmor(Integer headArmor) {
		this.headArmor = headArmor;
	}
	public Integer getChestArmor() {
		return chestArmor;
	}
	public void setChestArmor(Integer chestArmor) {
		this.chestArmor = chestArmor;
	}
	public Integer getAbdomenArmor() {
		return abdomenArmor;
	}
	public void setAbdomenArmor(Integer abdomenArmor) {
		this.abdomenArmor = abdomenArmor;
	}
	public Integer getLegArmor() {
		return legArmor;
	}
	public void setLegArmor(Integer legArmor) {
		this.legArmor = legArmor;
	}

	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getOnlineState() {
		return onlineState;
	}
	public void setOnlineState(boolean isOnline) {
		if(isOnline) {
			this.onlineState = CharacterOnlineState.ONLINE.toString();		
		} else {
			this.onlineState = CharacterOnlineState.OFFLINE.toString();	
		}
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}	

	public Integer getInventoryMax() {
		return inventoryMax;
	}
	public void setInventoryMax(Integer inventoryMax) {
		this.inventoryMax = inventoryMax;
	}
	public Integer getInventoryCurrent() {
		return inventoryCurrent;
	}
	public void setInventoryCurrent(Integer inventoryCurrent) {
		this.inventoryCurrent = inventoryCurrent;
	}

	public ItemMessage getWeapon() {
		return weapon;
	}
	public void setWeapon(ItemMessage weapon) {
		this.weapon = weapon;
	}
	public ItemMessage getShield() {
		return shield;
	}
	public void setShield(ItemMessage shield) {
		this.shield = shield;
	}
	public ItemMessage getHelmet() {
		return helmet;
	}
	public void setHelmet(ItemMessage helmet) {
		this.helmet = helmet;
	}
	public ItemMessage getMail() {
		return mail;
	}
	public void setMail(ItemMessage mail) {
		this.mail = mail;
	}
	public ItemMessage getBelt() {
		return belt;
	}
	public void setBelt(ItemMessage belt) {
		this.belt = belt;
	}
	public ItemMessage getBoots() {
		return boots;
	}
	public void setBoots(ItemMessage boots) {
		this.boots = boots;
	}		

}
