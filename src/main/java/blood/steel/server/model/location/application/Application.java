package blood.steel.server.model.location.application;

import java.util.*;

import blood.steel.server.model.character.*;
import blood.steel.server.model.fight.*;

public class Application {

	private ApplicationType type;
	private CharacterHandler owner;
	private int timeout;
	private List<CharacterHandler> teamRed; 	// active players
	private List<CharacterHandler> teamBlue;	// active players
	private int teamRedSize;
	private int teamBlueSize;
	
	public Application(ApplicationType type, CharacterHandler owner, int timeout, int redSize, int blueSize) {		
		setType(type);
		setOwner(owner);
		teamRed = Collections.synchronizedList(new ArrayList<CharacterHandler>());	
		teamBlue = Collections.synchronizedList(new ArrayList<CharacterHandler>());						
		this.timeout = timeout;		
		this.teamRedSize = redSize;
		this.teamBlueSize = blueSize;
		addToApplication(owner, Team.RED);
	}
		
	public ApplicationType getType() {
		return type;
	}
	public void setType(ApplicationType type) {
		this.type = type;
	}
	public CharacterHandler getOwner() {
		return owner;
	}
	public void setOwner(CharacterHandler owner) {
		this.owner = owner;
	}
	public int getTimeout() {
		return this.timeout;
	}
	
	public List<String> getRedNames() {
		List<String> names = new ArrayList<String>();
		synchronized(teamRed) {
			for(CharacterHandler character : teamRed) {
				names.add(character.getName());
			}
		}
		return names;
	}	
	public List<String> getBlueNames() {
		List<String> names = new ArrayList<String>();
		synchronized(teamBlue) {
			for(CharacterHandler character : teamBlue) {
				names.add(character.getName());
			}
		}
		return names;
	}
	
	public boolean addToApplication(CharacterHandler character, Team team) {
		if (team == Team.RED) {
			if(teamRed.size() < teamRedSize) {
				character.setCurrentApplication(this);
				teamRed.add(character);
				return true;
			} else {
				return false;
			}
		} else {
			if(teamBlue.size() < teamBlueSize) {
				character.setCurrentApplication(this);
				teamBlue.add(character);
				return true;
			} else {
				return false;
			}
		}
	}
	public boolean removeFromApplication(CharacterHandler character) {
		if (character.equals(owner)) {			
			erase();
			return true;
		} else if (teamRed.contains(character)) {
			teamRed.remove(character);
			character.removeFromCurrentApplication();			
			return true;
		} else if (teamBlue.contains(character)) {
			teamBlue.remove(character);
			character.removeFromCurrentApplication();			
			return true;
		} else {
			return false;
		}
	}
		
	public void erase() {
		for (CharacterHandler character : teamRed) {
				character.removeFromCurrentApplication();
		}				
		for (CharacterHandler character : teamBlue) {
				character.removeFromCurrentApplication();		
		}		
	}		
	
	public Fight commenceFight() {
		if(teamRed.size() == teamRedSize && teamBlue.size() == teamBlueSize && this.type != null) {
			this.erase();
			Fight fight = null;
			switch(this.type) {
				case DUEL:
					fight = new Fight(owner.getPlace(), this.timeout, new ArrayList<CharacterHandler>(teamRed), 
											new ArrayList<CharacterHandler>(teamBlue));
					break;
				default:
					fight = null;
			}
			return fight;
		} else {
			return null;
		}
	}
	
}
