package blood.steel.server.model.character.messaging;

import javax.xml.bind.annotation.*;

import blood.steel.server.model.character.CharacterHandler;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterShortMessage {

	@XmlValue
	private String name;
	@XmlAttribute
	private String gender;
	@XmlAttribute
	private String level;
	
	public CharacterShortMessage() {	}
	public CharacterShortMessage(CharacterHandler character) {
		this.name = character.getName();
		this.gender = character.getGender().toString().toLowerCase();
		this.level = String.valueOf(character.getLevel());
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}
