package blood.steel.server.controllers;

import java.util.*;

import blood.steel.server.communication.messaging.*;
import blood.steel.server.model.character.*;
import blood.steel.server.model.fight.*;
import blood.steel.server.model.listeners.CommunicationChannel;
import blood.steel.server.model.location.*;
import blood.steel.server.model.messaging.GameMessage;
import blood.steel.server.model.util.*;
import blood.steel.server.model.util.messaging.FightMessageFactory;
import blood.steel.server.model.util.messaging.GlobalMessageFactory;

public class CharacterController {
	
	private CharacterHandler character = null;
	private GameSession session = null;
	
	// constructor which creates a character if it does not exist
	public CharacterController(GameSession session, String name, CharacterType type, Sex gender) {
		this.session = session;
		CharacterFactory.createCharacter(type, name, gender);	// this call creates a character and stores it to the database
		initializeCharacter(name);
	}
	// constructor that retrieves the character if it should exist
	public CharacterController(GameSession session, String name) {
		this.session = session;
		initializeCharacter(name);
	}
	private void initializeCharacter(String name) {
		character = GameContext.findCharacter(name);	// finds character either in context or in database
		character.setOnline(new ChannelListener());		// sets character online, adds channel to transfer messages		
		character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createCharacterMessage(character));
		if (character.getState() == CharacterState.FIGHT) {
			character.onReceive(FightMessageFactory.getFightMessageFactory().createFightControlEnemyMessage(character.getCurrentFight().getCurrentEnemy(character)));	
			character.onReceive(FightMessageFactory.getFightMessageFactory().createFightMessageList(character.getCurrentFight().getMessageList(0)));
		}
		if (character.getState() == CharacterState.DEFEATED) {
			character.onReceive(FightMessageFactory.getFightMessageFactory().createFightMessageList(character.getCurrentFight().getMessageList(0)));
		}
	}
	
	public void shutdown() {
		SystemMessage message = new SystemMessage();
		message.setCode(19);
		message.setText("You are signed out!");
		this.session.getGameOutputStream().write(message);	
		this.session.getGameOutputStream().write(SystemMessageFactory.SOCKET_SHUTDOWN);			
	}
	
	//method responsible for global game actions, and some system messages
	public void performGlobalMethod(String request, Map<String, String> parameters) {
		if (request.equals("shutdown")) {
			shutdown();
		} else if (request.equals("get_character")) {
			if(parameters.get("name") == null) {
				character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createCharacterMessage(character));	
			} else {
				CharacterHandler otherCharacter = GameContext.findCharacter(parameters.get("name"));
				if (otherCharacter != null) {
					character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createCharacterOtherMessage(otherCharacter));	
				} else {			
					character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createCharacterNoStatMessage(parameters.get("name")));
				}				
			}
		}
	}	
	//method responsible for invoking character information and global character data
	public void performCharacterMethod(String request, Map<String, String> parameters) {
		character.executeCharacterRequest(request, parameters);			
	}	
	//method responsible for invoking fight methods rather then global or location methods
	public void performFightMethod(String request, Map<String, String> parameters) {
		if(character.getState() == CharacterState.FIGHT || character.getState() == CharacterState.DEFEATED) {
			if (request.equals("fight_attack")) {
				if(character.getState() == CharacterState.DEFEATED) {
					character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
				} else {
					character.getCurrentFight().attack(character, parameters.get("target"), 
																  AttackPlace.valueOf(parameters.get("attack").toUpperCase()), 
																  AttackPlace.valueOf(parameters.get("block1").toUpperCase()), 
																  AttackPlace.valueOf(parameters.get("block2").toUpperCase()));
				}
			} else if (request.equals("fight_finish")) {
				if (character.getState() == CharacterState.FIGHT) {
					character.getCurrentFight().finishEnemy(character);
				} else {
					character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
				}
			} else if (request.equals("fight_log")) {
				Integer value = 0;
				if(parameters.get("page") != null) {
					try {
						value = Integer.valueOf(parameters.get("page"));
					} catch(Exception ex) {
						value = 0;
					}
				} 
				character.onReceive(FightMessageFactory.getFightMessageFactory().createFightMessageList(
																				character.getCurrentFight().getMessageList(value)));				
			}
		} else {
			character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
		}
	}	
	// method responsible for invoking location methods rather than global methods
	public void performLocationMethod(String request, Map<String, String> parameters) {
		if (character.getState() == CharacterState.IDLE || character.getState() == CharacterState.APPLICATION) {
			World.getLocation(character.getPlace()).executeRequest(character, request, parameters);
		} else {
			character.onReceive(GlobalMessageFactory.getGlobalMessageFactory().createWrongStateMessage());
		}
	}
	/* listener class which holds the reference to current session */
	class ChannelListener implements CommunicationChannel {
		public void onReceive(GameMessage message) {
			session.getGameOutputStream().write(message);
		}		
	}
	
	// Controller clean-up method
	public void cleanup() {			
		character.setOffline();		
	}
	
}

