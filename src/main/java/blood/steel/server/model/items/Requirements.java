package blood.steel.server.model.items;

import blood.steel.server.model.character.CharacterHandler;

public class Requirements {
	
	/* requirements for the weapon */
	private int levelRequirement;
	private int strengthRequirement;
	private int agilityRequirement;
	private int luckRequirement;
	private int toughnessRequirement;
	
	public int getLevelRequirement() {
		return this.levelRequirement;
	}
	public void setLevelRequirement(int levelRequirement) {
		this.levelRequirement = levelRequirement;
	}
	public void setStrengthRequirement(int strengthRequirement) {
		this.strengthRequirement = strengthRequirement;
	}	
	public void setAgilityRequirement(int agilityRequirement) {
		this.agilityRequirement = agilityRequirement;
	}	
	public void setLuckRequirement(int luckRequirement) {
		this.luckRequirement = luckRequirement;
	}	
	public void setToughnessRequirement(int toughnessRequirement) {
		this.toughnessRequirement = toughnessRequirement;
	}	
	/* method to check if the item can be used by the character */
	public boolean isUsable(CharacterHandler character) {
		if(character.getLevel() < this.levelRequirement || character.getStrength() < this.strengthRequirement ||
					character.getAgility() < this.agilityRequirement || character.getLuck() < this.luckRequirement ||
					character.getToughness() < this.toughnessRequirement) {
			return false;
		} else {
			return true;
		}		
	}	
	
}
