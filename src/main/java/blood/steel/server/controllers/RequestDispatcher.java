package blood.steel.server.controllers;

import blood.steel.server.communication.request.*;

public class RequestDispatcher {
	
	public static RequestDispatcher newInstance() {
		return new RequestDispatcher();
	}
	
	public void sendRequest(Request request) {
		if (!request.getSession().isLogged()) { 							// player is not logged
			RegistrationModule.sendRequest(request);
		} else { 															// player is logged in
			if (request.getCommand().matches("^character.*")) {
				request.getSession().getCharacterController().performCharacterMethod(request.getCommand(), request.getParameters());
			} else if (request.getCommand().matches("^location.*")) {
				request.getSession().getCharacterController().performLocationMethod(request.getCommand(), request.getParameters());
			} else if (request.getCommand().matches("^fight.*")) {
				request.getSession().getCharacterController().performFightMethod(request.getCommand(), request.getParameters());
			} else {
				request.getSession().getCharacterController().performGlobalMethod(request.getCommand(), request.getParameters());
			}
		}
	}
	
}
