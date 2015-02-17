package blood.steel.server.model.fight.messaging;

import java.util.*;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class FightMessageList extends GameMessage {

	private int pageNumber;
	private List<FightMessage> message = new LinkedList<FightMessage>();

	public FightMessageList() {
		setCode(1010);
	}
	
	public List<FightMessage> getMessage() {
		return message;
	}
	public void setMessage(List<FightMessage> message) {
		this.message = message;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
}
