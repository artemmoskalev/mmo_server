package blood.steel.server.model.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import blood.steel.server.SystemResources;
import blood.steel.server.model.character.*;

public class GameContext {

	private static Map<String, CharacterHandler> characters;
	
	static {
		characters = new ConcurrentHashMap<String, CharacterHandler>();
	}
		
	public static void addCharacter(CharacterHandler character) {
		characters.put(character.getName(), character);
	}
	public static void removeCharacter(CharacterHandler character) {
		characters.remove(character.getName());
	}
	public static CharacterHandler findCharacter(String name) {	/* finds the character in context or either retrieves it from the database */
		CharacterHandler character = null;
		EntityManager em = null;
		if (!characters.containsKey(name)) {
			try {
				em = SystemResources.getPersistenceContext().createEntityManager();
				character = em.find(Hero.class, name);			
			} finally {
				em.close();
			}
		} else {
			character = characters.get(name);
		}
		return character;
	}
	
}