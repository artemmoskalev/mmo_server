package blood.steel.server.model.fight.messaging;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

import blood.steel.server.model.fight.AttackPlace;

@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class AttackMessage {

	private String type;
	private String attacker;
	private String receiver;
	private String attackPlace;
	@XmlTransient
	private String block1;
	@XmlTransient
	private String block2;
	private String damage;
	private Integer hpLeft;
	private Integer hp;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setType(AttackType type) {
		this.type = type.toString().toLowerCase();
	}	
	public String getAttacker() {
		return attacker;
	}
	public void setAttacker(String attacker) {
		this.attacker = attacker;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}	
	public String getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = String.valueOf(damage);
	}
	public String getAttackPlace() {
		return attackPlace;
	}
	public void setAttackPlace(AttackPlace attackPlace) {
		this.attackPlace = attackPlace.toString().toLowerCase();
	}
	public String getBlock1() {
		return block1;
	}
	public void setBlock1(AttackPlace block1) {
		this.block1 = block1.toString().toLowerCase();
	}
	public String getBlock2() {
		return block2;
	}
	public void setBlock2(AttackPlace block2) {
		this.block2 = block2.toString().toLowerCase();
	}
	public Integer getHpLeft() {
		return hpLeft;
	}
	public void setHpLeft(int hpLeft) {
		this.hpLeft = hpLeft;
	}
	public Integer getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
