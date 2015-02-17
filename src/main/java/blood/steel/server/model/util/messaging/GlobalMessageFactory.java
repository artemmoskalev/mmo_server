package blood.steel.server.model.util.messaging;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.character.messaging.CharacterMessage;
import blood.steel.server.model.messaging.GameMessage;
import blood.steel.server.model.messaging.InformationMessage;

public class GlobalMessageFactory {

	private static GlobalMessageFactory factory = new GlobalMessageFactory();
	
	private GlobalMessageFactory() {  }
	
	public static GlobalMessageFactory getGlobalMessageFactory() {
		return factory;
	}

	/**
	 * Creates a message with the stats of the player`s own character
	 */
	public GameMessage createCharacterMessage(CharacterHandler character) {
		CharacterMessage message = new CharacterMessage(character);
		message.setCode(101);
		return message;
	}
	/**
	 * Creates a message with the stats of the specified character
	 */
	public GameMessage createCharacterOtherMessage(CharacterHandler otherCharacter) {
		CharacterMessage message = new CharacterMessage(otherCharacter);
		message.setCode(102);
		return message;
	}
	/**
	 * Creates a message stating that there is no such character information
	 */
	public GameMessage createCharacterNoStatMessage(String name) {
		CharacterMessage message = new CharacterMessage(name);
		message.setCode(103);
		return message;
	}
	/**
	 * Creates a message stating that the command cannot be issued because of the character`s current state
	 */
	public GameMessage createWrongStateMessage() {
		InformationMessage message = new InformationMessage("Wrong state!");
		message.setCode(200);
		return message;
	}	
	
}
