package blood.steel.server.model.items;

import javax.persistence.*;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.util.WeaponFactory;

@Entity
public abstract class Item {
	
	@Id
	private long id;
	private String name;
	@Transient
	private Integer basicPrice;
	@Transient
	private Integer weight;
	@Transient
	private String description;
	
	@Transient
	private Requirements requirements;
	
	@Override
	public int hashCode() {
		return (int)id;
	}
	@Override	
	public boolean equals(Object o) {
		if(o instanceof Item && ((Item)o).getId() == this.id) {
			return true;
		} else {
			return false;
		}
	}
	
	@PostLoad
	public void initItemProperties() {
		basicPrice = Integer.valueOf(WeaponFactory.getWeaponProperty(this.name, "price"));
		weight = Integer.valueOf(WeaponFactory.getWeaponProperty(this.name, "weight"));
		description = WeaponFactory.getWeaponProperty(this.name, "description");
		
		requirements = new Requirements();
		
		String level = WeaponFactory.getWeaponProperty(name, "levelRequirement");
		if(level != null) {
			requirements.setLevelRequirement(Integer.valueOf(level));
		}
		String strength = WeaponFactory.getWeaponProperty(name, "strengthRequirement");
		if(strength != null) {
			requirements.setStrengthRequirement(Integer.valueOf(strength));
		}
		String agility = WeaponFactory.getWeaponProperty(name, "agilityRequirement");
		if(agility != null) {
			requirements.setAgilityRequirement(Integer.valueOf(agility));
		}
		String luck = WeaponFactory.getWeaponProperty(name, "luckRequirement");
		if(luck != null) {
			requirements.setLuckRequirement(Integer.valueOf(luck));
		}
		String toughness = WeaponFactory.getWeaponProperty(name, "toughnessRequirement");
		if(toughness != null) {
			requirements.setToughnessRequirement(Integer.valueOf(toughness));
		}		
	}	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBasicPrice() {
		return basicPrice;
	}
	public void setBasicPrice(int basicPrice) {
		this.basicPrice = basicPrice;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Requirements getRequirements() {
		return requirements;
	}
	public void setRequirements(Requirements requirements) {
		this.requirements = requirements;
	}			
	
	public abstract void useItem(CharacterHandler character);
	
}
