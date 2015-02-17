package blood.steel.server.model.items.weapons;

import javax.persistence.Entity;

import blood.steel.server.model.character.Equipment;

@Entity
public class Mail extends Weaponry {

	@Override
	public void insertIntoSlot(Equipment equipment) {
		equipment.setMail(this);
	}
	@Override
	public void removeFromSlot(Equipment equipment) {
		equipment.removeMail();
	}
	
}
