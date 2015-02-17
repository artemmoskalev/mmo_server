package blood.steel.server.model.items;

import javax.xml.bind.JAXB;

import blood.steel.server.model.listeners.*;
import blood.steel.server.model.messaging.GameMessage;

public class DummyListener implements CommunicationChannel {

	@Override
	public void onReceive(GameMessage message) {
		JAXB.marshal(message, System.out);
	}
	
}
