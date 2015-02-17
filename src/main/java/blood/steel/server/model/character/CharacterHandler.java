package blood.steel.server.model.character;

import java.util.*;

import blood.steel.server.model.fight.Fight;
import blood.steel.server.model.items.Item;
import blood.steel.server.model.listeners.CommunicationChannel;
import blood.steel.server.model.location.LocationType;
import blood.steel.server.model.location.application.Application;

public interface CharacterHandler extends CommunicationChannel {

	// methods to set the character name and type
	public String getName();
	public void setName(String name);
	public CharacterType getRace();
	public void setRace(CharacterType race);
	public Sex getGender();
	public void setGender(Sex gender);
	// methods representing the character`s modifiable characteristics
	public int getLevel();
	public void setLevel(int level);
	public int getExperience();
	public void setExperience(int experience);
	public int getMinDamage();
	public void setMinDamage(int minDamage);	
	public int getMaxDamage();
	public void setMaxDamage(int maxDamage);
	public int getHp();
	public void setHp(int hp);
	public int getCurrentHp();
	public void setCurrentHp(int currentHp);
	public int getStrength();
	public void setStrength(int strength);
	public int getAgility();
	public void setAgility(int agility);
	public int getLuck();	
	public void setLuck(int luck);
	public int getToughness();
	public void setToughness(int toughness);	
	// characteristics which are not directly modifiable
	public int getCritical();
	public int getAntiCritical();
	public int getDodge();
	public int getAntiDodge();
	public void setCritical(int critical);
	public void setAntiCritical(int antiCritical);
	public void setDodge(int dodge);
	public void setAntiDodge(int antiDodge);	
	public int getHeadArmor();
	public int getChestArmor();
	public int getAbdomenArmor();
	public int getLegArmor();	
	public void setHeadArmor(int headArmor);
	public void setChestArmor(int chestArmor);
	public void setAbdomenArmor(int abdomenArmor);
	public void setLegArmor(int legArmor);		
	// game controller methods
	public int getGold();
	public void setGold(int gold);
	public LocationType getPlace();
	public void setPlace(LocationType place);
	public void moveTo(LocationType place);
	public void setOnline(CommunicationChannel channel);
	public void setOffline();
	public boolean isOnline();
	public CharacterState getState();
	public void setState(CharacterState state);
	public String getImage();
	public void setImage(String image);
	// fight and application details, implemented by actor	
	public Application getCurrentApplication();
	public void setCurrentApplication(Application applicationOwner);
	public void removeFromCurrentApplication();
	public Fight getCurrentFight();
	public void setCurrentFight(Fight currentFight);
	public void removeFromCurrentFight();
	// game flow external hooks - implemented by Actor
	public void executeCharacterRequest(String request, Map<String, String> parameters);
	public void persistCharacter();
	/* weapon section */
	public Equipment getEquipment();
	public void setEquipment(Equipment equipment);
	/* item section */
	public void setInventory(Inventory inventory);	
	public boolean addItem(Item article);
	public void removeItem(Item article);
	public List<Item> getAllItems();
	public int getInventoryMaxSize();
	public int getInventoryCurrentSize();
	public void useInventoryItem(long id, CharacterHandler target);
	
}
