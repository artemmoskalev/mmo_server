package blood.steel.server.model.util;

import blood.steel.server.model.character.*;

public abstract class CharacterFactory {
	
	/*
	 * creates the character and saves it into the database. Then, it can be retrieved with the GameContext.
	 * */
	public static void createCharacter(CharacterType type, String name, Sex gender) {
		CharacterHandler character = null;
		switch(type) {
			case ELF:
				character = new Elf(name, gender);
				break;
			case ORC:
				character = new Orc(name, gender);
				break;
			case HUMAN:
				character = new Human(name, gender);
				break;
			case UNDEAD:
				character = new Undead(name, gender);
				break;
		}
		character.persistCharacter();
	}
	
}
