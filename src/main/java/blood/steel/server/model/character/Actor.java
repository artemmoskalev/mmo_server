package blood.steel.server.model.character;

import java.util.*;
import java.util.concurrent.*;

import javax.persistence.*;

import blood.steel.server.SystemResources;
import blood.steel.server.model.fight.Fight;
import blood.steel.server.model.listeners.CommunicationChannel;
import blood.steel.server.model.location.*;
import blood.steel.server.model.location.application.*;
import blood.steel.server.model.messaging.*;
import blood.steel.server.model.util.*;
import blood.steel.server.model.util.messaging.CharacterMessageFactory;


/* This class is used as a controller medium between the Hero and
 * the CharacterHandler class. It generally means, that methods implemented here
 *r are used to control the behavior of the Hero but not its internal structure
 * because Hero is a JPA entity to save in the database *  
 */

public abstract class Actor implements CharacterHandler {

	// bridge class to transfer messages from player classes to controllers
	private CommunicationChannel channel;
	// Fight and Application details
	private Fight currentFight;
	private Application currentApplication;
	// Protocol commands for this character
	private Map<String, CharacterCommand> characterCommands;
	
	public Actor() {		
		characterCommands = new ConcurrentHashMap<String, CharacterCommand>();
		characterCommands.put("character_move_to", new MoveTo());
		characterCommands.put("character_list_items", new ListInventory());
		characterCommands.put("character_use_item", new UseItem());
	}
	public void persistCharacter() {
		EntityManager em = null;
		try {
			em = SystemResources.getPersistenceContext().createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.merge(this);
			tx.commit();
		} catch(Exception ex) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}	
	
	// observer-pattern methods for listening and channeling messages to output	
	protected void setChannel(CommunicationChannel channel) { // available only from subclass of hero
		this.channel = channel;								  // to install during online transition
	}
	public void onReceive(GameMessage message) {	
		this.channel.onReceive(message);
	}
	
	// fight and application details
	public Application getCurrentApplication() {
		return currentApplication;
	}
	public void setCurrentApplication(Application application) {
		this.currentApplication = application;
		setState(CharacterState.APPLICATION);
	}
	public void removeFromCurrentApplication() {
		this.currentApplication = null;
		setState(CharacterState.IDLE);
		if(!isOnline()) {
			GameContext.removeCharacter(this);
			persistCharacter();
		}	
	}
	public Fight getCurrentFight() {
		return currentFight;
	}
	public void setCurrentFight(Fight currentFight) {
		this.currentFight = currentFight;
		setState(CharacterState.FIGHT);
	}	
	public void removeFromCurrentFight() {
		this.setCurrentHp(this.getHp()); // to remove!
		this.currentFight = null;
		setState(CharacterState.IDLE);
		if(!isOnline()) {
			GameContext.removeCharacter(this);
			persistCharacter();
		}
	}
		
	// method for executing the required command on the character
	public void executeCharacterRequest(String request, Map<String, String> parameters) {
		CharacterCommand job = characterCommands.get(request);
		if (job != null) {
			job.execute(parameters);
		} else {
			this.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createCharacterNoCommandMessage(request));
		}
	}
	
	/* classes for performing character-dependent global operations */
	class MoveTo implements CharacterCommand {
		public void execute(Map<String, String> parameters) {
			LocationType newPlace = LocationType.valueOf(parameters.get("location").toUpperCase());
			if (getPlace() != newPlace) {			
				moveTo(newPlace);
			}
		}		
	}
	
	class ListInventory implements CharacterCommand {
		public void execute(Map<String, String> parameters) {
			onReceive(CharacterMessageFactory.getCharacterMessageFactory().createInventoryItemListMessage(getAllItems()));
		}		
	}
	
	class UseItem implements CharacterCommand {
		public void execute(Map<String, String> parameters) {
			long id = Long.valueOf(parameters.get("id"));
			useInventoryItem(id, Actor.this);
		}		
	}
		
}
