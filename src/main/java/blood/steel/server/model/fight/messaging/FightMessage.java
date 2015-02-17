package blood.steel.server.model.fight.messaging;

import java.util.*;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
@Entity
@Table(name="FIGHT_MESSAGE")
public class FightMessage extends GameMessage {
		
	@Id @GeneratedValue	
	@XmlTransient
	private long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private int page;
	@ElementCollection
	@CollectionTable(name="FIGHT_MESSAGE_ATTACK")
	private List<AttackMessage> attack;
	@ElementCollection
	@CollectionTable(name="FIGHT_MESSAGE_ATTACKEVENT")
	private List<FightEventMessage> event;
	@ElementCollection
	@CollectionTable(name="FIGHT_MESSAGE_FIGHTSTATEEVENT")
	private List<FightStateMessage> fightState;
	
	public FightMessage() {
		setCode(1003);		
		this.date = new Date();
		this.attack = new ArrayList<AttackMessage>();
		this.event = new ArrayList<FightEventMessage>();
		this.fightState = new ArrayList<FightStateMessage>();
	}
		
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}		
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<AttackMessage> getAttack() {
		return attack;
	}
	public void setAttack(List<AttackMessage> attack) {
		this.attack = attack;
	}
	public List<FightEventMessage> getEvent() {
		return event;
	}
	public void setEvent(List<FightEventMessage> event) {
		this.event = event;
	}
	public List<FightStateMessage> getfightState() {
		return fightState;
	}
	public void setfightState(List<FightStateMessage> fightState) {
		this.fightState = fightState;
	}
	
	public void addAttack(AttackMessage attack) {
		if(this.attack.size() < 2) {
			this.attack.add(attack);
		}
	}	
	public void addEvent(FightEventMessage event) {
		this.event.add(event);		
	}
	public void addFightStateEvent(FightStateMessage fightState) {
		if(this.fightState.size() < 1) {
			this.fightState.add(fightState);
		}
	}	
	
}
