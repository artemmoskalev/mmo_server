package blood.steel.server.model.character;

import java.util.Map;

public interface CharacterCommand {
	public void execute(Map<String, String> parameters);
}
