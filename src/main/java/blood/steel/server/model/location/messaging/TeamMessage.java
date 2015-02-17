package blood.steel.server.model.location.messaging;

import java.util.*;

public class TeamMessage {

	private List<String> character;

	public TeamMessage() {	}
	public TeamMessage(List<String> characters) {
		this.character = characters;
	}
	
	public List<String> getCharacter() {
		return character;
	}
	public void setCharacter(List<String> character) {
		this.character = character;
	}
	
}
