package blood.steel.server.model.location.messaging;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.character.messaging.CharacterShortMessage;

@XmlRootElement(name="gameMessage")
public class LocationPeopleMessage extends LocationMessage {

	private List<CharacterShortMessage> character;

	public LocationPeopleMessage() {	}
	
	/* constructors to create the required message */
	public LocationPeopleMessage(List<CharacterShortMessage> character) {
		this.character = character;
	}
	public LocationPeopleMessage(CharacterShortMessage character) {
		this.character = new ArrayList<CharacterShortMessage>();
		this.character.add(character);
	}
	
	public List<CharacterShortMessage> getCharacter() {
		return character;
	}
	public void setCharacter(List<CharacterShortMessage> character) {
		this.character = character;
	}
	
}
