package blood.steel.server.model.location.trainingroom.messaging;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.location.application.*;
import blood.steel.server.model.location.messaging.LocationMessage;

@XmlRootElement(name="gameMessage")
public class DuelApplicationMessage extends LocationMessage {

	private List<DuelMessage> duel = new LinkedList<DuelMessage>();
	
	public DuelApplicationMessage() {		}
	
	public DuelApplicationMessage(Application application) {
		duel.add(new DuelMessage(application));
	}
	
	public void addDuelMessage(Application application) {
		duel.add(new DuelMessage(application));
	}
	public List<DuelMessage> getDuel() {
		return duel;
	}
	public void setDuel(List<DuelMessage> duel) {
		this.duel = duel;
	}
			
}
