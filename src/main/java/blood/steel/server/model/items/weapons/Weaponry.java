package blood.steel.server.model.items.weapons;

import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

import blood.steel.server.model.character.*;
import blood.steel.server.model.items.Item;
import blood.steel.server.model.util.WeaponFactory;
import blood.steel.server.model.util.messaging.CharacterMessageFactory;

@Entity
public abstract class Weaponry extends Item {

	/* bonus characteristics of the weapon */
	@Transient
	private Integer damage;	
	@Transient
	private Integer hp;
	
	@Transient
	private Integer strength;
	@Transient
	private Integer agility;
	@Transient
	private Integer luck;
	@Transient
	private Integer toughness;
	
	@Transient
	private Integer critical;
	@Transient
	private Integer antiCritical;
	@Transient
	private Integer dodge;
	@Transient
	private Integer antiDodge;	
	
	@Transient
	private Integer headArmor;
	@Transient
	private Integer chestArmor;
	@Transient
	private Integer abdomenArmor;
	@Transient
	private Integer legArmor;
	
	@PostLoad
	public void initWeaponProperties() {
		String damage = WeaponFactory.getWeaponProperty(getName(), "damage");
		if(damage != null) {
			setDamage(Integer.valueOf(damage));
		}
		String hp = WeaponFactory.getWeaponProperty(getName(), "hp");
		if(hp != null) {
			setHp(Integer.valueOf(hp));
		}
		
		String strength = WeaponFactory.getWeaponProperty(getName(), "strength");
		if(strength != null) {
			setStrength(Integer.valueOf(strength));
		}
		String agility = WeaponFactory.getWeaponProperty(getName(), "agility");
		if(agility != null) {
			setAgility(Integer.valueOf(agility));
		}
		String luck = WeaponFactory.getWeaponProperty(getName(), "luck");
		if(luck != null) {
			setLuck(Integer.valueOf(luck));
		}
		String toughness = WeaponFactory.getWeaponProperty(getName(), "toughness");
		if(toughness != null) {
			setToughness(Integer.valueOf(toughness));
		}
		
		String critical = WeaponFactory.getWeaponProperty(getName(), "critical");
		if(critical != null) {
			setCritical(Integer.valueOf(critical));
		}
		String antiCritical = WeaponFactory.getWeaponProperty(getName(), "antiCritical");
		if(antiCritical != null) {
			setAntiCritical(Integer.valueOf(antiCritical));
		}
		String dodge = WeaponFactory.getWeaponProperty(getName(), "dodge");
		if(dodge != null) {
			setDodge(Integer.valueOf(dodge));
		}
		String antiDodge = WeaponFactory.getWeaponProperty(getName(), "antiDodge");
		if(antiDodge != null) {
			setAntiDodge(Integer.valueOf(antiDodge));
		}
		
		String headArmor = WeaponFactory.getWeaponProperty(getName(), "headArmor");
		if(headArmor != null) {
			setHeadArmor(Integer.valueOf(headArmor));
		}
		String chestArmor = WeaponFactory.getWeaponProperty(getName(), "chestArmor");
		if(chestArmor != null) {
			setChestArmor(Integer.valueOf(chestArmor));
		}
		String abdomenArmor = WeaponFactory.getWeaponProperty(getName(), "abdomenArmor");
		if(abdomenArmor != null) {
			setAbdomenArmor(Integer.valueOf(abdomenArmor));
		}
		String legArmor = WeaponFactory.getWeaponProperty(getName(), "legArmor");
		if(legArmor != null) {
			setLegArmor(Integer.valueOf(legArmor));
		}
	}
	
	@Override
	public void useItem(CharacterHandler character) {
		if(character.getEquipment().isEquipped(this)) {			
			character.getEquipment().unequipItem(this);
		} else {
			if(getRequirements().isUsable(character)) { 
				character.getEquipment().equipItem(this);
			} else {
				character.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemCannotBeUsedMessage(this));
			}			
		}
	}
	/**
	 * Standard implementation of Visitor pattern in order to insert weapons into correct slots
	 */
	public abstract void insertIntoSlot(Equipment equipment);
	public abstract void removeFromSlot(Equipment equipment);
	
	public void setDamage(int damage) {
		this.damage = damage;
	}	
	public void setHp(int hp) {
		this.hp = hp;
	}	
	public void setStrength(int strength) {
		this.strength = strength;
	}	
	public void setAgility(int agility) {
		this.agility = agility;
	}	
	public void setLuck(int luck) {
		this.luck = luck;
	}	
	public void setToughness(int toughness) {
		this.toughness = toughness;
	}	
	public void setCritical(int critical) {
		this.critical = critical;
	}	
	public void setAntiCritical(int antiCritical) {
		this.antiCritical = antiCritical;
	}	
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}	
	public void setAntiDodge(int antiDodge) {
		this.antiDodge = antiDodge;
	}	
	public void setHeadArmor(int headArmor) {
		this.headArmor = headArmor;
	}	
	public void setChestArmor(int chestArmor) {
		this.chestArmor = chestArmor;
	}	
	public void setAbdomenArmor(int abdomenArmor) {
		this.abdomenArmor = abdomenArmor;
	}	
	public void setLegArmor(int legArmor) {
		this.legArmor = legArmor;
	}
	
	public void equip(CharacterHandler character) {
		if(damage != null) {
			character.setMinDamage(character.getMinDamage() + this.damage);
			character.setMaxDamage(character.getMaxDamage() + this.damage);
		}
		if(hp != null) {
			character.setHp(character.getHp() + this.hp);
		}
		
		if(strength != null) {
			character.setStrength(character.getStrength() + this.strength);
		}
		if(agility != null) {
			character.setAgility(character.getAgility() + this.agility);
		}
		if(luck != null) {
			character.setLuck(character.getLuck() + this.luck);
		}
		if(toughness != null) {
			character.setToughness(character.getToughness() + this.toughness);
		}
		
		if(critical != null) {
			character.setCritical(character.getCritical() + this.critical);
		}
		if(antiCritical != null) {
			character.setAntiCritical(character.getAntiCritical() + this.antiCritical);
		}
		if(dodge != null) {
			character.setDodge(character.getDodge() + this.dodge);
		}
		if(antiDodge != null) {
			character.setAntiDodge(character.getAntiDodge() + this.antiDodge);
		}
		
		if(headArmor != null) {
			character.setHeadArmor(character.getHeadArmor() + this.headArmor);
		}
		if(chestArmor != null) {
			character.setChestArmor(character.getChestArmor() + this.chestArmor);
		}
		if(abdomenArmor != null) {
			character.setAbdomenArmor(character.getAbdomenArmor() + this.abdomenArmor);
		}
		if(legArmor != null) {
			character.setLegArmor(character.getLegArmor() + this.legArmor);
		}
	}	
	public void unequip(CharacterHandler character) {
		if(damage != null) {
			character.setMinDamage(character.getMinDamage() - this.damage);
			character.setMaxDamage(character.getMaxDamage() - this.damage);
		}
		if(hp != null) {
			character.setHp(character.getHp() - this.hp);
		}
		
		if(strength != null) {
			character.setStrength(character.getStrength() - this.strength);
		}
		if(agility != null) {
			character.setAgility(character.getAgility() - this.agility);
		}
		if(luck != null) {
			character.setLuck(character.getLuck() - this.luck);
		}
		if(toughness != null) {
			character.setToughness(character.getToughness() - this.toughness);
		}
		
		if(critical != null) {
			character.setCritical(character.getCritical() - this.critical);
		}
		if(antiCritical != null) {
			character.setAntiCritical(character.getAntiCritical() - this.antiCritical);
		}
		if(dodge != null) {
			character.setDodge(character.getDodge() - this.dodge);
		}
		if(antiDodge != null) {
			character.setAntiDodge(character.getAntiDodge() - this.antiDodge);
		}
		
		if(headArmor != null) {
			character.setHeadArmor(character.getHeadArmor() - this.headArmor);
		}
		if(chestArmor != null) {
			character.setChestArmor(character.getChestArmor() - this.chestArmor);
		}
		if(abdomenArmor != null) {
			character.setAbdomenArmor(character.getAbdomenArmor() - this.abdomenArmor);
		}
		if(legArmor != null) {
			character.setLegArmor(character.getLegArmor() - this.legArmor);
		}
	}
	
}
