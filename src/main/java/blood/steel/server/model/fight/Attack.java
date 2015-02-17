package blood.steel.server.model.fight;

import blood.steel.server.model.character.CharacterHandler;
import blood.steel.server.model.fight.messaging.*;

public class Attack {

	private CharacterHandler attacker;
	private CharacterHandler receiver;
	private AttackPlace attack;
	private AttackPlace block, secondBlock;
	
	public CharacterHandler getAttacker() {
		return attacker;
	}
	public void setAttacker(CharacterHandler attacker) {
		this.attacker = attacker;
	}
	public CharacterHandler getReceiver() {
		return receiver;
	}
	public void setReceiver(CharacterHandler receiver) {
		this.receiver = receiver;
	}
	public AttackPlace getAttack() {
		return attack;
	}
	public void setAttack(AttackPlace attack) {
		this.attack = attack;
	}
	public AttackPlace getBlock() {
		return block;
	}
	public void setBlock(AttackPlace block) {
		this.block = block;
	}
	public AttackPlace getSecondBlock() {
		return secondBlock;
	}
	public void setSecondBlock(AttackPlace secondBlock) {
		this.secondBlock = secondBlock;
	}
	// method for attacking
	public AttackMessage dealDamage(boolean isBlocked) {
		
		AttackMessage response = new AttackMessage();
		response.setAttacker(attacker.getName());
		response.setReceiver(receiver.getName());		
		
		// calculates base damage
		int damage = BattleFormula.calculateDamage(attacker);	
		damage = damage - (int)(damage*BattleFormula.calculateReduction(receiver, attack));	
		// decide which type of message is produced
		// chooses between dodge, critical, blocked and normal
		if (BattleFormula.isDodge(attacker, receiver)) {			
			response.setType(AttackType.DODGE);		
			damage = 0;
		} else if (!BattleFormula.isCritical(attacker, receiver)) {
			if (isBlocked) {
				response.setType(AttackType.BLOCK);
				damage = 0;
			} else {
				response.setType(AttackType.NORMAL);
			}			
		} else {
			if (!isBlocked) {
				response.setType(AttackType.CRITICAL);
				damage = damage*2;
			} else {
				response.setType(AttackType.BREAK_BLOCK);
			}
		}
		// check if there is more damage than the player can receive
		if (damage > receiver.getCurrentHp()) {
			damage = receiver.getCurrentHp();
		}
		
		// setting damage field of message and block and attack types
		response.setAttackPlace(attack);
		response.setBlock1(block);
		response.setBlock2(secondBlock);		
		
		// setting hp of the receiver
		receiver.setCurrentHp(receiver.getCurrentHp()-damage);	
		
		// setting currentHp and totalHp information to the message, as well as damage
		response.setDamage(damage);
		response.setHpLeft(receiver.getCurrentHp());
		response.setHp(receiver.getHp());
		
		return response;
	}	
	// end of attack method	
	
}
