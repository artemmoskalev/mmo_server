package blood.steel.server.model.fight;

import blood.steel.server.model.character.CharacterHandler;

public class BattleFormula {

	// calculates the effects of armor on the attack - character is the receiver
	public static double calculateReduction(CharacterHandler character, AttackPlace attack) {
		int armor = 0;
		switch (attack) {
			case HEAD:
					armor += character.getHeadArmor();
					break;
			case CHEST:
					armor += character.getChestArmor();
					break;
			case ABDOMEN:
					armor += character.getAbdomenArmor();
					break;
			case LEGS:
					armor += character.getLegArmor();
					break;		
		}		
		return 0.004*(armor + character.getToughness())/(1 + 0.004*(armor + character.getToughness()));
	}
	
	// calculates the damage of the character
	public static int calculateDamage(CharacterHandler character) {
		return (character.getMinDamage()+(int)(Math.random()*(character.getMaxDamage() + character.getStrength()/2 - 
				character.getMinDamage() - character.getStrength()/4)));
	}
	
	// calculates if the critical strike is possible
	public static boolean isCritical(CharacterHandler attacker, CharacterHandler receiver) {
		boolean isCritical = false;
		int criticalDifference = attacker.getCritical() + attacker.getLuck()*3 - receiver.getAntiCritical() - receiver.getLuck()*3;
		if(criticalDifference > 0) {
			isCritical = (Math.random() < (0.01*(criticalDifference)/(1 + 0.01*criticalDifference)));
		}
		if(Math.random() < 0.05) {
			isCritical = true;
		}
		if(Math.random() < 0.05) {
			isCritical = false;
		}
		return isCritical;
	}	
	
	// calculate if the dodge is possible
	public static boolean isDodge(CharacterHandler attacker, CharacterHandler receiver) {
		boolean isDodge = false;
		int dodgeDifference = receiver.getDodge() + receiver.getAgility()*3 - attacker.getAntiDodge() - attacker.getAgility()*3;
		if(dodgeDifference > 0) {
			isDodge = (Math.random() < (0.01*(dodgeDifference)/(1 + 0.01*dodgeDifference)));
		}
		if(Math.random() < 0.05) {
			isDodge = true;
		}
		if(Math.random() < 0.05) {
			isDodge = false;
		}
		return isDodge;
	}	
	
}
