package blood.steel.server.model.listeners;

import blood.steel.server.model.messaging.GameMessage;

public interface CommunicationChannel {
	public void onReceive(GameMessage message);	
}
