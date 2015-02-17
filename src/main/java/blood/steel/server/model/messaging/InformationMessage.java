package blood.steel.server.model.messaging;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gameMessage")
public class InformationMessage extends GameMessage {

	private String text;
	
	public InformationMessage() {	}	
	public InformationMessage(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
