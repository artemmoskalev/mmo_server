package blood.steel.server.model.fight.messaging;

import javax.xml.bind.annotation.XmlRootElement;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.messaging.GameMessage;

@XmlRootElement(name="gameMessage")
public class CurrentEnemyMessage extends GameMessage {

	private String name;
	private String level;
	private String sex;
	
	private String hp;
	private String currentHp;
	
	public CurrentEnemyMessage() {  }
	public CurrentEnemyMessage(CharacterHandler character) {
		this.name = character.getName();
		this.level = String.valueOf(character.getLevel());
		this.sex = character.getGender().toString().toLowerCase();
		
		this.hp = String.valueOf(character.getHp());
		this.currentHp = String.valueOf(character.getCurrentHp());		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getCurrentHp() {
		return currentHp;
	}
	public void setCurrentHp(String currentHp) {
		this.currentHp = currentHp;
	}
	
}
