package blood.steel.server.model.location;

import java.util.*;
import java.util.concurrent.*;

import blood.steel.server.model.character.*;
import blood.steel.server.model.fight.*;
import blood.steel.server.model.location.application.*;
import blood.steel.server.model.util.messaging.GlobalMessageFactory;

public class TrainingRoom extends Location {
	
	private Map<String, Application> duelList;
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	public TrainingRoom() {		
		super(LocationType.TRAININGROOM);
		duelList = new HashMap<String, Application>();
		// adding commands to the location
		locationCommands.put("location_duel_application_list", new DuelApplicationList());
		locationCommands.put("location_register_duel_application", new RegisterDuelApplication());
		locationCommands.put("location_remove_from_duel_application", new RemoveFromDuelApplication());
		locationCommands.put("location_add_to_duel_application", new AddToDuelApplication());		
		locationCommands.put("location_start_duel_application", new StartDuelApplication());
	}
			
	/* timer to control the life of the application */
	class ScheduledApplicationRemover implements Runnable {		
		private CharacterHandler character;		
		public ScheduledApplicationRemover(CharacterHandler character) {
			this.character = character;
		}		
		public void run() {
			synchronized(TrainingRoom.this) {
				if (duelList.get(character.getName()) != null) {
					removeFromDuelApplication(character);
				}
			}
		}
	}
	
	/*methods used by inner classes and responsible for responding to incoming requests*/
	public synchronized void getDuelApplicationList(CharacterHandler character) {
		character.onReceive(factory.createDuelListMessage(duelList));			
	}	
	public synchronized void registerDuelApplication(CharacterHandler character, int duration, int timeout) {	
		duelList.put(character.getName(), new Application(ApplicationType.DUEL, character, timeout, 1, 1));
		distributeMessageAll(factory.createDuelRegisteredMessage(character.getCurrentApplication()));
		executor.schedule(new ScheduledApplicationRemover(character), duration, TimeUnit.MINUTES);		
	}
	public synchronized void removeFromDuelApplication(CharacterHandler character) {
		if (character.getCurrentApplication().getType() == ApplicationType.DUEL) {
			String owner = character.getCurrentApplication().getOwner().getName();
			if (!owner.equals(character.getName())) {
				character.getCurrentApplication().removeFromApplication(character); 
				distributeMessageAll(factory.createDuelRemoveCharacterMessage(owner, character));				 
			} else {
				Application application = duelList.remove(character.getName());					
				application.removeFromApplication(character);	
				distributeMessageAll(factory.createDuelRemovedMessage(application));
			}
		} 
	}
	public synchronized void addToDuelApplication(CharacterHandler character, String owner) {
		if (duelList.get(owner) != null) {
			if (duelList.get(owner).addToApplication(character, Team.BLUE)) {
				distributeMessageAll(factory.createDuelAddCharacterMessage(owner, character));
			} else {
				character.onReceive(factory.createDuelAddCharacterFailureMessage(owner, character));
			}	
		} else {
			character.onReceive(factory.createDuelAddCharacterNotExistMessage());
		}		
	}	
	public synchronized void startDuel(CharacterHandler character) {
		if (duelList.get(character.getName()) != null) {
			Application application = duelList.get(character.getName());
			Fight startedDuel = application.commenceFight();
			if (startedDuel != null) {
				duelList.remove(character.getName());
				distributeMessageAll(factory.createDuelRemovedMessage(application));
			} else {
				character.onReceive(factory.createDuelStartFailureMessage(application));						
			}
		} else {
			character.onReceive(factory.createDuelStartNotAuthorizedMessage());
		}		
	}
		
	// Executors for different commands
	/*
	 * get the list of duels in the training room
	 * */
	class DuelApplicationList implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			getDuelApplicationList(character);
		}		
	}	
	/*
	 * registers new fight in the location or removes it
	 * */
	class RegisterDuelApplication implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			if (character.getState() == CharacterState.IDLE) {
				registerDuelApplication(character, Integer.valueOf(parameters.get("duration")), 
												   Integer.valueOf(parameters.get("timeout")));					
			} else {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
			}
		}		
	}
	/*
	 * add player to the existing duel fight application or removes him
	 */
	class RemoveFromDuelApplication implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			if (character.getState() == CharacterState.APPLICATION) {
				removeFromDuelApplication(character);
			} else {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
			}
		}
	}	
	class AddToDuelApplication implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			if (character.getState() == CharacterState.IDLE) {
				addToDuelApplication(character, parameters.get("owner"));
			} else {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
			}
		}		
	}		
	/*
	 * start duel fight
	 */
	class StartDuelApplication implements Command {
		public void execute(CharacterHandler character, Map<String, String> parameters) {
			if (character.getState() == CharacterState.APPLICATION) {
				startDuel(character);
			} else {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
			}
		}		
	}
	
}
