package blood.steel.server.model.fight;

import java.util.*;
import javax.persistence.*;

import blood.steel.server.model.fight.messaging.*;
import blood.steel.server.model.location.LocationType;

@Entity
@Table(name="FIGHT")
public class FightLog {
	
	@Id
	private long id;		
	private int timeout;
	@Enumerated(EnumType.STRING)
	private LocationType location;
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	@ElementCollection
	@CollectionTable(name="FIGHT_RED")
	private List<String> red;		// list of player names of red team for the JPA information
	@ElementCollection
	@CollectionTable(name="FIGHT_BLUE")
	private List<String> blue;		// list of player names of blue team for the JPA information
	@OneToMany(cascade={CascadeType.ALL})
	@JoinColumn(table = "FIGHT_MESSAGE")
	@OrderColumn
	private List<FightMessage> message;		// list of all messages in the fight
	
	@Transient
	private int counter = 0;
	@Transient
	private int currentPage = 1;
	
	public FightLog() {				// JPA Constructor
		this.red = new ArrayList<String>();	
		this.blue = new ArrayList<String>();
		this.start = new Date();
		this.message = new ArrayList<FightMessage>();
	}		
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}	
	
	public LocationType getLocation() {
		return location;
	}
	public void setLocation(LocationType location) {
		this.location = location;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
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
	public List<FightMessage> getMessage() {
		return message;
	}
	public void setMessage(List<FightMessage> message) {
		this.message = message;
	}	
	public void addMessage(FightMessage newMessage) {		
		newMessage.setPage(currentPage);
		message.add(newMessage);
		counter++;
		if(counter >= 10) {
			counter = 0;
			currentPage++;
		}
	}
	
	@PrePersist
	public void endLog() {
		this.end = new Date();
	}
	
}
