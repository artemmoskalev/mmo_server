package blood.steel.server.model.location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.fight.*;
import blood.steel.server.model.messaging.*;
import blood.steel.server.model.util.messaging.LocationMessageFactory;

public abstract class Location {

	private final LocationType location;
	protected final List<CharacterHandler> people; 			// list of people in current location
	protected final Map<String, Command> locationCommands;	// list of commands for this location
	protected final Map<Long, Fight> fightsInProgress;		// list of fights going on in the location
	protected final LocationMessageFactory factory;
	
	public Location(LocationType location) {
		this.location = location;
		this.people = Collections.synchronizedList(new ArrayList<CharacterHandler>());
		locationCommands = new ConcurrentHashMap<String, Command>();
		fightsInProgress = new ConcurrentHashMap<Long, Fight>();
		/* generic command list for all locations */
		locationCommands.put("location_people_list", new LocationPeopleList());
		factory = LocationMessageFactory.newLocationMessageFactory(location);
	}
	public LocationType getLocation() {
		return location;
	}
			
	public void executeRequest(CharacterHandler character, String request, Map<String, String> parameters) {
		Command job = locationCommands.get(request);
		if (job != null) {
			job.execute(character, parameters);
		} else {
			character.onReceive(factory.createNoLocationCommandMessage(request));
		}
	}
			
	/* methods to track and notify players about the set of people in the location */
	public synchronized void addCharacter(CharacterHandler character) {			
		distributeMessageAll(factory.createCharacterAddedMessage(character));			
		people.add(character);			
		character.onReceive(factory.createCharacterListMessage(people));			
	}
	public synchronized void removeCharacter(CharacterHandler character) {		
		people.remove(character);
		distributeMessageAll(factory.createCharacterRemovedMessage(character));	
	}
		
	/* utility method to distribute messages to all characters in location */
	public synchronized void distributeMessageAll(GameMessage message) {
		for (CharacterHandler character : people) {
			character.onReceive(message);
		}		
	}
	
	/* generic location commands that are available to all locations */
	class LocationPeopleList implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			synchronized(Location.this) {
				character.onReceive(factory.createCharacterListMessage(people));
			}			
		}		
	}	
	
	/* method to add new fights into the location and removing them */
	public synchronized void addFight(Fight fight) {
		fightsInProgress.put(fight.getId(), fight);
		distributeMessageAll(factory.createFightAddedMessage(fight));
	}
	public synchronized void removeFight(Fight fight) {
		fightsInProgress.remove(fight.getId());
		distributeMessageAll(factory.createFightRemovedMessage(fight));
	}		
	
}
