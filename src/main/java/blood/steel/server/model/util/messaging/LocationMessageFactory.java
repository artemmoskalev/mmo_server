package blood.steel.server.model.util.messaging;

import java.util.*;
import java.util.Map.Entry;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.character.messaging.CharacterShortMessage;
import blood.steel.server.model.fight.Fight;
import blood.steel.server.model.location.LocationType;
import blood.steel.server.model.location.application.Application;
import blood.steel.server.model.location.messaging.*;
import blood.steel.server.model.location.shop.messaging.*;
import blood.steel.server.model.location.trainingroom.messaging.*;
import blood.steel.server.model.messaging.*;

public class LocationMessageFactory {

	private LocationType location;
	
	private LocationMessageFactory(LocationType location) {
		this.location = location;
	}
	
	public static LocationMessageFactory newLocationMessageFactory(LocationType location) {
		return new LocationMessageFactory(location);
	}
	/**
	 * Creates a message with the list of people in the current location
	 */
	public GameMessage createCharacterListMessage(List<CharacterHandler> people) {
		List<CharacterShortMessage> list = new LinkedList<CharacterShortMessage>();
		for(CharacterHandler character : people) {
			list.add(new CharacterShortMessage(character));
		}
		LocationPeopleMessage message = new LocationPeopleMessage(list);
		message.setCode(301);
		message.setLocation(location);
		return message;
	}	
	/**
	 * Creates a message with the name of the character who joins the room
	 */
	public GameMessage createCharacterAddedMessage(CharacterHandler character) {
		LocationPeopleMessage message = new LocationPeopleMessage(new CharacterShortMessage(character));
		message.setCode(302);
		message.setLocation(location);
		return message;
	}
	/**
	 * Creates a message with the name of the character who leaves the room
	 */
	public GameMessage createCharacterRemovedMessage(CharacterHandler character) {
		LocationPeopleMessage message = new LocationPeopleMessage(new CharacterShortMessage(character));
		message.setCode(303);
		message.setLocation(location);
		return message;
	}	
	/**
	 * Creates a message which contains the information about a new fight that has started in the location
	 */
	public GameMessage createFightAddedMessage(Fight fight) {
		FightNotificationMessage message = new FightNotificationMessage(fight);
		message.setCode(351);
		return message;
	}	
	/**
	 * Creates a message which contains the information about the fight which has just ended in the location
	 */
	public GameMessage createFightRemovedMessage(Fight fight) {
		FightNotificationMessage message = new FightNotificationMessage(fight);
		message.setCode(352);
		return message;
	}	
	/**
	 * Creates a message stating that such a command does not exist in the current location
	 */
	public GameMessage createNoLocationCommandMessage(String request) {
		InformationMessage message = new InformationMessage(request);
		message.setCode(300);
		return message;
	}

	/**
	 * Creates a message with the list of duel applications in the training room
	 */
	public GameMessage createDuelListMessage(Map<String, Application> duelList) {
		DuelApplicationMessage message = new DuelApplicationMessage();
		for (Entry<String, Application> entry : duelList.entrySet()) {
			message.addDuelMessage(entry.getValue());
		}		
		message.setCode(401);
		message.setLocation(location);
		return message;
	}
	/**
	 * Creates a message confirming that the duel application has been registered
	 */
	public GameMessage createDuelRegisteredMessage(Application application) {
		DuelApplicationMessage message = new DuelApplicationMessage(application);
		message.setCode(402);
		message.setLocation(location);
		return message;
	}
	/**
	 * Creates a message confirming that the duel application has been removed
	 */
	public GameMessage createDuelRemovedMessage(Application application) {
		DuelApplicationMessage message = new DuelApplicationMessage(application);
		message.setCode(403);
		message.setLocation(location);
		return message;
	}
	/**
	 * Creates a message showing which player has been added to the duel application
	 */
	public GameMessage createDuelAddCharacterMessage(String owner, CharacterHandler character) {
		DuelApplicationChangeMessage message = new DuelApplicationChangeMessage(owner, character.getName());
		message.setCode(404);
		message.setLocation(location);
		return message;
	}
	/**
	 * Creates a message stating that a player cannot be added to the duel application because it is full
	 */
	public GameMessage createDuelAddCharacterFailureMessage(String owner, CharacterHandler character) {
		DuelApplicationChangeMessage message = new DuelApplicationChangeMessage(owner, character.getName());
		message.setCode(405);
		message.setLocation(location);
		return message;
	}
	/**
	 * Creates a message stating that the duel application the player tries to add his character to does not exist
	 */
	public GameMessage createDuelAddCharacterNotExistMessage() {
		InformationMessage message = new InformationMessage("Duel you are trying to join does not exist");
		message.setCode(406);
		return message;
	}
	/**
	 * Creates a message showing that the player has been removed from the duel application
	 */
	public GameMessage createDuelRemoveCharacterMessage(String owner, CharacterHandler character) {
		DuelApplicationChangeMessage message = new DuelApplicationChangeMessage(owner, character.getName());
		message.setCode(407);
		message.setLocation(location);
		return message;
	}	
	/**
	 * Creates a message stating that the player is not the owner of the application to start
	 */
	public GameMessage createDuelStartNotAuthorizedMessage() {
		InformationMessage message = new InformationMessage("You cant start the application which is not yours!");
		message.setCode(408);
		return message;
	}	
	/**
	 * Creates a message stating that the duel could not be started as long as application is not full with players
	 */
	public GameMessage createDuelStartFailureMessage(Application temp) {
		DuelApplicationMessage message = new DuelApplicationMessage(temp);
		message.setCode(409);
		message.setLocation(location);
		return message;
	}	
	/**
	 * Creates a message stating that the player does not have enough gold to buy the item
	 */
	public GameMessage createNotEnoughGoldMessage() {
		LocationInformationMessage message = new LocationInformationMessage();
		message.setCode(501);
		message.setText("Not enough gold to buy item!");
		return message;
	}
	/**
	 * Creates a message stating that the chosen item is out of stock of the shop
	 */
	public GameMessage createOutOfStockMessage() {
		LocationInformationMessage message = new LocationInformationMessage();
		message.setCode(502);
		message.setText("Item is out of stock!");
		return message;
	}
	
	/**
	 * Creates a message which contains the weapon names and their quantity for a particular level and item type
	 */
	public GameMessage createItemList(Map<String, Integer> itemList) {
		ItemListMessage message = new ItemListMessage();
		message.setCode(500);
		for(Map.Entry<String, Integer> entry : itemList.entrySet()) {
			message.addItem(new ItemQuantity(entry.getKey(), entry.getValue()));
		}
		return message;
	}
	
}
