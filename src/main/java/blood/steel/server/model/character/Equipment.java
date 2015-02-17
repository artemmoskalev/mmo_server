package blood.steel.server.model.character;

import javax.persistence.*;

import blood.steel.server.model.items.weapons.Weaponry;
import blood.steel.server.model.util.messaging.CharacterMessageFactory;

@Embeddable
public class Equipment {
	
	@Transient
	private CharacterHandler owner;
	
	@OneToOne(cascade={CascadeType.ALL})
	private Weaponry weapon;
	@OneToOne(cascade={CascadeType.ALL})
	private Weaponry shield;
	@OneToOne(cascade={CascadeType.ALL})
	private Weaponry helmet;
	@OneToOne(cascade={CascadeType.ALL})
	private Weaponry mail;
	@OneToOne(cascade={CascadeType.ALL})
	private Weaponry belt;
	@OneToOne(cascade={CascadeType.ALL})
	private Weaponry boots;
	
	public void setOwner(CharacterHandler owner) {
		this.owner = owner;
	}
	
	/**
	 * This method checks if any of the items in the slots cannot be used anymore, and removes them recursively by
	 * using reEquip in conjunction with removeXXX methods.
	 */
	private void reEquip() {
		if(this.weapon != null) {
			if(!this.weapon.getRequirements().isUsable(owner)) {
				removeWeapon();
			}
		}
		if(this.shield != null) {
			if(!this.shield.getRequirements().isUsable(owner)) {
				removeShield();
			}
		}
		if(this.helmet != null) {
			if(!this.helmet.getRequirements().isUsable(owner)) {
				removeHelmet();
			}
		}
		if(this.mail != null) {
			if(!this.mail.getRequirements().isUsable(owner)) {
				removeMail();
			}
		}
		if(this.belt != null) {
			if(!this.belt.getRequirements().isUsable(owner)) {
				removeBelt();
			}
		}
		if(this.boots != null) {
			if(!this.boots.getRequirements().isUsable(owner)) {
				removeBoots();
			}
		}
	}
	
	public Weaponry getWeapon() {
		return weapon;
	}
	public void setWeapon(Weaponry weapon) {
		if(this.weapon == null) {
			this.weapon = weapon;
			this.weapon.equip(owner);
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedMessage(this.weapon, "weapon"));
		} else {
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedFailureMessage("weapon"));
		}
	}
	public void removeWeapon() {		
		this.weapon.unequip(owner);
		owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemUnequippedMessage(this.weapon, "weapon"));
		this.weapon = null;
		reEquip();
	}
	public Weaponry getShield() {
		return shield;
	}
	public void setShield(Weaponry shield) {
		if(this.shield == null) {
			this.shield = shield;
			this.shield.equip(owner);
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedMessage(this.shield, "shield"));
		} else {
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedFailureMessage("shield"));
		}
	}
	public void removeShield() {
		this.shield.unequip(owner);
		owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemUnequippedMessage(this.shield, "shield"));
		this.shield = null;
		reEquip();
	}
	public Weaponry getHelmet() {
		return helmet;
	}
	public void setHelmet(Weaponry helmet) {
		if(this.helmet == null) {
			this.helmet = helmet;
			this.helmet.equip(owner);
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedMessage(this.helmet, "helmet"));
		} else {
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedFailureMessage("helmet"));
		}
	}
	public void removeHelmet() {
		this.helmet.unequip(owner);
		owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemUnequippedMessage(this.helmet, "helmet"));
		this.helmet = null;
		reEquip();
	}
	public Weaponry getMail() {
		return mail;
	}
	public void setMail(Weaponry mail) {
		if(this.mail == null) {
			this.mail = mail;
			this.mail.equip(owner);
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedMessage(this.mail, "mail"));
		} else {
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedFailureMessage("mail"));
		}
	}
	public void removeMail() {
		this.mail.unequip(owner);
		owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemUnequippedMessage(this.mail, "mail"));
		this.mail = null;
		reEquip();
	}
	public Weaponry getBelt() {
		return belt;
	}
	public void setBelt(Weaponry belt) {
		if(this.belt == null) {
			this.belt = belt;
			this.belt.equip(owner);
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedMessage(this.belt, "belt"));
		} else {
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedFailureMessage("belt"));
		}
	}
	public void removeBelt() {
		this.belt.unequip(owner);
		owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemUnequippedMessage(this.belt, "belt"));
		this.belt = null;
		reEquip();
	}
	public Weaponry getBoots() {
		return boots;
	}
	public void setBoots(Weaponry boots) {
		if(this.boots == null) {
			this.boots = boots;
			this.boots.equip(owner);
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedMessage(this.boots, "boots"));
		} else {
			owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemEquippedFailureMessage("boots"));
		}
	}
	public void removeBoots() {
		this.boots.unequip(owner);
		owner.onReceive(CharacterMessageFactory.getCharacterMessageFactory().createItemUnequippedMessage(this.boots, "boots"));
		this.boots = null;
		reEquip();
	}
	
	public void equipItem(Weaponry item) {
		item.insertIntoSlot(this);		
	}
	public boolean isEquipped(Weaponry item) {		
		if((getWeapon() != null && weapon.equals(item)) || (getShield() != null && shield.equals(item)) || 
		   (getHelmet() != null && helmet.equals(item)) || (getMail() != null && mail.equals(item)) || 
		   (getBelt() != null && belt.equals(item)) || (getBoots() != null && boots.equals(item))) {
			return true;
		} else {
			return false;
		}
	}
	public void unequipItem(Weaponry item) {
		item.removeFromSlot(this);
	}
	
}
