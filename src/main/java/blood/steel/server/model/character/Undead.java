package blood.steel.server.model.character;

import javax.persistence.Entity;

import blood.steel.server.model.location.LocationType;

@Entity
public class Undead extends Hero {

	public Undead() {}
	
	public Undead(String name, Sex gender) {
		this.setName(name);
		this.setRace(CharacterType.UNDEAD);
		this.setGender(gender);
		this.setLevel(1);
		this.setExperience(0);
		this.setMinDamage(2);
		this.setMaxDamage(5);
		this.setHp(30);		
		this.setStrength(3);
		this.setAgility(3);
		this.setLuck(3);
		this.setToughness(3);
		this.setCurrentHp(this.hp);
		this.setGold(30);
		this.setPlace(LocationType.TRAININGROOM);
		this.setState(CharacterState.IDLE);
		if(gender == Sex.MALE) {
			this.setImage("undead_male_base");
		} else {
			this.setImage("undead_female_base");
		}
		this.setEquipment(new Equipment());
		this.setInventory(new Inventory(30));
	}
	
}
