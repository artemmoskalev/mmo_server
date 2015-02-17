package blood.steel.server.model.character;

import java.util.List;

import javax.persistence.*;

import blood.steel.server.model.items.*;
import blood.steel.server.model.listeners.*;
import blood.steel.server.model.location.*;
import blood.steel.server.model.util.GameContext;
import blood.steel.server.model.util.messaging.CharacterMessageFactory;

@Entity
@Table(name="HERO")
public abstract class Hero extends Actor {
	//visible characteristics	
	@Id
	protected String name;
	@Enumerated(EnumType.STRING)
	protected CharacterType race;
	@Enumerated(EnumType.STRING)
	private Sex gender;
	
	protected int level;
	protected int experience;
	protected int minDamage;
	protected int maxDamage;	
	protected int hp;
	protected int currentHp;
	
	protected int strength;
	protected int agility;
	protected int luck;
	protected int toughness;
	// invisible characteristics
	protected int critical;
	protected int antiCritical;
	protected int dodge;
	protected int antiDodge;	
	
	protected int headArmor = 0;
	protected int chestArmor = 0;
	protected int abdomenArmor = 0;
	protected int legArmor = 0;		
	
	// callback for initializing equipment
	
	/* weapon and inventory section */
	
	protected Equipment equipment;
	
	@PostLoad
	public void init() {
		this.equipment.setOwner(this);
	}
	
	public Equipment getEquipment() {
		return this.equipment;
	}
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}
	
	protected Inventory inventory; 
		
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public boolean addItem(Item article) {
		if(inventory.addItem(article)) {
			this.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemReceivedMessage(article));
			return true;
		} else {
			this.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createInventoryFullMessage());
			return false;
		}
	}
	public void removeItem(Item article) {
		if(inventory.removeItem(article)) {
			this.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemRemovedMessage(article));
		} else {
			this.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemRemovedFailureMessage(article));
		}
	}
	public List<Item> getAllItems() {
		return inventory.getItemList();
	}
	public int getInventoryMaxSize() {
		return inventory.getTotalInventorySize();
	}
	public int getInventoryCurrentSize() {
		return inventory.getCurrentInventorySize();
	}
	public void useInventoryItem(long id, CharacterHandler target) {
		Item item = inventory.getItemById(id);
		item.useItem(target);
	}
	
	/* weapon and inventory section end */
	
	// game controller parameters	
	protected int gold;
	@Enumerated(EnumType.STRING)
	protected LocationType place; // sets location where character is	
	@Enumerated(EnumType.STRING)
	protected CharacterOnlineState onlineState; // sets the online/offline state of the character
	@Enumerated(EnumType.STRING)
	protected CharacterState state; // sets the state in which the character is when he is online
	protected String image;	// image of the player`s avatar
	
	// overriding equals and hashCode	
	public boolean equals(Object o) {
		if (o instanceof Hero && ((Hero)o).getName().equals(this.getName())) {
			return true;
		} else {
			return false;
		}
	}	
	public int hashCode() {
		return this.getName().hashCode();
	}
	
	// methods for setting the character`s name and type
	public String getName() {		
		return this.name;
	}	
	public void setName(String name) {
		this.name = name;		 
	}
	public CharacterType getRace() {
		return race;
	}
	public void setRace(CharacterType race) {
		this.race = race;
	}	
	public Sex getGender() {
		return gender;
	}
	public void setGender(Sex gender) {
		this.gender = gender;
	}
	
	// methods for manipulating the character`s stats
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}	
	public int getExperience() {
		return this.experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getMinDamage() {
		return this.minDamage;
	}
	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}
	public int getMaxDamage() {
		return this.maxDamage;
	}
	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}
	public int getHp() {
		return this.hp;
	}
	public void setHp(int hp) {
		if (this.currentHp > hp) {
			this.currentHp = hp;
		}
		this.hp = hp;		
	}
	public int getCurrentHp() {
		return this.currentHp;
	}
	public void setCurrentHp(int currentHp) {
		if (currentHp < 0) {
			this.currentHp = 0;
		} else if (currentHp > this.hp) {
			this.currentHp = this.hp;
		} else {
			this.currentHp = currentHp;
		}
		if (this.currentHp == 0 && this.state == CharacterState.FIGHT) {
			setState(CharacterState.DEFEATED);
		}
	}
	
	public int getStrength() {
		return this.strength;
	}	
	public void setStrength(int strength) {
		this.strength = strength;
	}	
	public int getAgility() {
		return this.agility;
	}	
	public void setAgility(int agility) {
		this.agility = agility;			
	}	
	public int getLuck() {
		return this.luck;
	}	
	public void setLuck(int luck) {
		this.luck = luck;		
	}	
	public int getToughness() {
		return this.toughness;
	}
	public void setToughness(int toughness) {
		if (toughness > this.toughness) {			
			this.hp += (toughness - this.toughness)*5;			
		} else {
			this.hp -= (this.toughness - toughness)*5;
		}
		this.toughness = toughness;
	}
	
	public int getCritical() {
		return critical;
	}
	public void setCritical(int critical) {
		this.critical = critical;
	}
	public int getAntiCritical() {
		return antiCritical;
	}
	public void setAntiCritical(int antiCritical) {
		this.antiCritical = antiCritical;
	}
	public int getDodge() {
		return dodge;
	}
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}
	public int getAntiDodge() {
		return antiDodge;
	}
	public void setAntiDodge(int antiDodge) {
		this.antiDodge = antiDodge;
	}	
	public int getHeadArmor() {
		return this.headArmor;
	}
	public void setHeadArmor(int headArmor) {
		this.headArmor = headArmor;
	}
	public int getChestArmor() {		
		return this.chestArmor;
	}
	public void setChestArmor(int chestArmor) {
		this.chestArmor = chestArmor;
	}
	public int getAbdomenArmor() {		
		return this.abdomenArmor;
	}
	public void setAbdomenArmor(int abdomenArmor) {
		this.abdomenArmor = abdomenArmor;
	}
	public int getLegArmor() {
		return this.legArmor;
	}
	public void setLegArmor(int legArmor) {
		this.legArmor = legArmor;
	}
	
	// additional game controller parameters
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public LocationType getPlace() {
		return place;
	}
	public void setPlace(LocationType place) {
		this.place = place;
	}	
	public void moveTo(LocationType place) {
		World.getLocation(this.place).removeCharacter(this);	
		World.getLocation(place).addCharacter(this);
		setPlace(place);
	}
	public void setOnline(CommunicationChannel channel) {
		setChannel(channel);
		this.onlineState = CharacterOnlineState.ONLINE;		
		World.getLocation(place).addCharacter(this);
		GameContext.addCharacter(this);
	}
	public void setOffline() {
		this.onlineState = CharacterOnlineState.OFFLINE;
		World.getLocation(this.place).removeCharacter(this);	
		if(this.getState() == CharacterState.IDLE) {
			GameContext.removeCharacter(this);
			this.persistCharacter();
		}
	}
	public boolean isOnline() {
		if(this.onlineState == CharacterOnlineState.ONLINE) {
			return true;
		} else {
			return false;
		}
	}
	public CharacterState getState() {
		return state;
	}
	public void setState(CharacterState state) {
		this.state = state;
	}	
	public String getImage() {
		return this.image;
	}
	public void setImage(String image) {
		this.image = image;
	}		
		
}
