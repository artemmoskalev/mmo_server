package blood.steel.server.controllers;

import blood.steel.server.authentication.*;
import blood.steel.server.communication.messaging.SystemMessageFactory;
import blood.steel.server.communication.request.*;
import blood.steel.server.model.character.*;

public class RegistrationModule {
	
	public static void sendRequest(Request request) {
		if (request.getSession().isUnknown()) { 	// player is unknown. either register or login
			if (request.getCommand().equals("login")) { 					   // check if the request is to login a player
				Register.login(request.getSession().getPlayer(), request.getCommand(), request.getParameters(), request.getSession().getGameOutputStream());
				if (request.getSession().isLogged()) {
						CharacterController controller = new CharacterController(request.getSession(), 
																				 request.getSession().getPlayer().getLogin());
						request.getSession().setCharacterController(controller);
				}					
			} else if (request.getCommand().equals("register")) { 				
				Register.register(request.getSession().getPlayer(), request.getCommand(), 
								  request.getParameters(), 
								  request.getSession().getGameOutputStream());
			} else {
				request.getSession().getGameOutputStream().write(SystemMessageFactory.COMMAND_MISPLACE);
				request.getSession().getGameOutputStream().write(SystemMessageFactory.SOCKET_SHUTDOWN);
			}
		} else if (request.getSession().isRegistering()) {
			if (request.getCommand().equals("create")) {
				CharacterController controller = new CharacterController(request.getSession(), 
																		 request.getSession().getPlayer().getLogin(), 
																		 CharacterType.valueOf(request.getParameters().get("type").toUpperCase()), 
																		 Sex.valueOf(request.getParameters().get("gender").toUpperCase())); 
				request.getSession().getPlayer().setState(PlayerState.LOGGED);	
				request.getSession().setCharacterController(controller);
			} else { 						// triggered when the player is waiting registration but not registered yet
				request.getSession().getGameOutputStream().write(SystemMessageFactory.COMMAND_MISPLACE);
				request.getSession().getGameOutputStream().write(SystemMessageFactory.SOCKET_SHUTDOWN);
			}					
		}			
	}
	
}
