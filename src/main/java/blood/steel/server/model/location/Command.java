package blood.steel.server.model.location;

import java.util.Map;

import blood.steel.server.model.character.CharacterHandler;

public interface Command {

	public void execute(CharacterHandler character, Map<String, String> parameters);
	
}
