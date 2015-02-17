package blood.steel.server.model.fight.messaging;

import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class FightStateMessage {

	private String type;
	@Transient
	private List<String> red;
	@Transient
	private List<String> blue;
	private String winner;
	
	public FightStateMessage() {	
		this.red = new ArrayList<String>();		
		this.blue = new ArrayList<String>();
	}	
	public FightStateMessage(String type, List<String> red, List<String> blue, String winner) {	
		this.type = type;
		this.red = red;		
		this.blue = blue;
		this.winner = winner;
	}
	public FightStateMessage(String type, List<String> red, List<String> blue) {	
		this.type = type;
		this.red = red;		
		this.blue = blue;
	}	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	public List<String> getRed() {
		return red;
	}
	public void setRed(List<String> red) {
		this.red = red;
	}
	public List<String> getBlue() {
		return blue;
	}
	public void setBlue(List<String> blue) {
		this.blue = blue;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}	

}
