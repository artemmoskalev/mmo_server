package blood.steel.server.model.fight;

import java.util.*;
import java.util.concurrent.*;

import javax.persistence.*;

import blood.steel.server.SystemResources;
import blood.steel.server.model.character.*;
import blood.steel.server.model.fight.messaging.*;
import blood.steel.server.model.location.*;
import blood.steel.server.model.util.messaging.FightMessageFactory;

/**
 * Fight is the superclass of all fights. Responsible for controlling the 
 * fight, managing the lifecycle of itself, logging the results of the fight
 * @author Artem Moskalev 
 */
/**
 * @author Artem
 *
 */
public class Fight {	
	
	class AttackKey {		
		private CharacterHandler attacker;
		private CharacterHandler receiver;
		private boolean reversed;
		
		public AttackKey(CharacterHandler attacker, CharacterHandler receiver) {
			this.attacker = attacker;
			this.receiver = receiver;
			reversed = false;
		}		
		@Override
		public boolean equals(Object o) {
			if(o instanceof AttackKey 
			   && ((AttackKey)o).getAttacker().equals(this.attacker) 
			   && ((AttackKey)o).getReceiver().equals(this.receiver)) {
				return true;
			} else {
				return false;
			}
		}		
		@Override
		public int hashCode() {
			return (this.attacker.hashCode() + this.receiver.hashCode());
		}
		
		public CharacterHandler getAttacker() {
			return this.attacker;
		}
		public CharacterHandler getReceiver() {
			return this.receiver;
		}
		public void reverse() {
			CharacterHandler temp = this.attacker;
			this.attacker = this.receiver;
			this.receiver = temp;
			if(!this.reversed) {
				this.reversed = true;
			} else {
				this.reversed = false;
			}
		}
		public boolean isReversed() {
			return this.reversed;
		}	
		
	}
	
	private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private FightLog log;
	
	private List<CharacterHandler> teamRed; 	// active players
	private List<CharacterHandler> teamRedDefeated;	//defeated players
	private List<CharacterHandler> teamBlue;	// active players
	private List<CharacterHandler> teamBlueDefeated;	//defeated players
	private List<CharacterHandler> fighters;	
	
	private Map<CharacterHandler, CharacterHandler> currentEnemy;
	private Map<AttackKey, Attack> attacks;	
	
	private Map<CharacterHandler, Future<?>> timeouts;
	private CharacterHandler finishLastRed;
	private CharacterHandler finishLastBlue;
	
	/**
	 * @return returns the id of the current fight
	 */
	public long getId() {
		return log.getId();
	}
	public void setId(long id) {
		log.setId(id);
	}
	public LocationType getLocation() {
		return log.getLocation();
	}
	public void setLocation(LocationType location) {
		log.setLocation(location);
	}
	public int getTimeout() {
		return this.log.getTimeout();
	}
	/**
	 * The constructor of the Fight. Sets teams of players, initiates the fight log, 
	 * creates a storage for attacks and current enemies.
	 * @param location shows which Location the Fight is held in
	 * @param timeout is the maximum time, which a player is allowed to be idle before automatic attack
	 * @param initialRed is the red team of players. It must be a separate list, because it is modified inside the fight
	 * @param initialBlue is the blue team of players. It must be a separate list, because it is modified inside the fight
	 */
	public Fight(LocationType location, int timeout, List<CharacterHandler> initialRed, List<CharacterHandler> initialBlue) {	
		
		/* this section initializes the fields of the fight, populates the lists, etc. */
		this.log = new FightLog();
		this.log.setTimeout(timeout);
		setId(FightSequenceGenerator.getInstance().getNewFightId());
		setLocation(location);
		fighters = Collections.synchronizedList(new ArrayList<CharacterHandler>());				
		teamRed = Collections.synchronizedList(initialRed);	
		for (CharacterHandler character : teamRed) {			
			log.getRed().add(character.getName());
			fighters.add(character);			
		}
		teamBlue = Collections.synchronizedList(initialBlue);
		for (CharacterHandler character : teamBlue) {			
			log.getBlue().add(character.getName());
			fighters.add(character);			
		}
		teamRedDefeated = Collections.synchronizedList(new ArrayList<CharacterHandler>());	
		teamBlueDefeated = Collections.synchronizedList(new ArrayList<CharacterHandler>());	
		
		currentEnemy = new ConcurrentHashMap<CharacterHandler, CharacterHandler>();
		attacks = new ConcurrentHashMap<AttackKey, Attack>();		
		
		timeouts = new HashMap<CharacterHandler, Future<?>>(); // holds the references for pending asynchronous events for characters
		
		// sets all fighters in the fight state
		for (CharacterHandler character : fighters) {
			character.setCurrentFight(this);
		}		
		
		/* this section is responsible for setting current enemies when the fight starts */
		List<CharacterHandler> redTemporary = new ArrayList<CharacterHandler>(initialRed);
		List<CharacterHandler> blueTemporary = new ArrayList<CharacterHandler>(initialBlue);
		
		while (redTemporary.size() > 0 && blueTemporary.size() > 0) {
			int redIndex = (int)(Math.random() * redTemporary.size());
			int blueIndex = (int)(Math.random() * blueTemporary.size());
		
			CharacterHandler first = redTemporary.remove(redIndex);
			CharacterHandler second = blueTemporary.remove(blueIndex);
		
			currentEnemy.put(first, second);
			currentEnemy.put(second, first);
		}
		// this part iterates through uneven team remains, in order to set opponents for the remaining players if necessary
		if (redTemporary.size() == 0 && blueTemporary.size() > 0) {
			while(blueTemporary.size() > 0) {
				CharacterHandler blueLeft = blueTemporary.remove(0);
				CharacterHandler opponent = initialRed.get((int)Math.random()*initialRed.size());
				currentEnemy.put(blueLeft, opponent);
			}
		} else if (blueTemporary.size() == 0 && redTemporary.size() > 0) {
			while(redTemporary.size() > 0) {
				CharacterHandler redLeft = redTemporary.remove(0);
				CharacterHandler opponent = initialBlue.get((int)Math.random()*initialBlue.size());
				currentEnemy.put(redLeft, opponent);
			}
		}
		// send a message to every participant about which opponent they have 
		for (CharacterHandler fighter : fighters) {
			fighter.onReceive(FightMessageFactory.getFightMessageFactory().createFightControlEnemyMessage(getCurrentEnemy(fighter)));
		}
		
		/* this section finalizes the fight creation, sends the fight-start notifications to participants, saves the fight in the location */
		FightMessage startMessage = new FightMessage();
		startMessage.addFightStateEvent(new FightStateMessage("fight_start", getRedNames(), getBlueNames()));
		log.addMessage(startMessage);
		
		for (CharacterHandler character : fighters) {
			character.onReceive(startMessage);
		}	
		World.getLocation(getLocation()).addFight(this);	
	}	
		
	/**
	 * Method identifies which team - red/blue - the player belongs to
	 * @param character is the player whose team needs to be found
	 * @return returns the team of players which the player is currently with. 
	 * In case the player is defeated or not in a fight, returns null.
	 */
	public List<CharacterHandler> getCharacterTeam(CharacterHandler character) {
		if(teamRed.contains(character) || teamRedDefeated.contains(character)) {
			return this.teamRed;
		} else if(teamBlue.contains(character) || teamBlueDefeated.contains(character)) {
			return this.teamBlue;
		} else {
			return null;
		}
	}	
	/**
	 * Method identifies which team - red/blue - is the player`s enemy team
	 * @param character is the player whose team needs to be found
	 * @return returns the enemy list of the current player. 
	 * In case the player is defeated or not in a fight, returns null.
	 */
	public List<CharacterHandler> getEnemyTeam(CharacterHandler character) {
		if(teamRed.contains(character) || teamRedDefeated.contains(character)) {
			return this.teamBlue;
		} else if(teamBlue.contains(character) || teamBlueDefeated.contains(character)) {
			return this.teamRed;
		} else {
			return null;
		}
	}		
	/**
	 * @return returns the copy of the List containing the names 
	 * of the players of the red team in the fight
	 */
	public List<String> getRedNames() {
		synchronized(log.getRed()) {
			return new ArrayList<String>(log.getRed());
		}
	}
	/**
	 * @return returns the copy of the List containing the names 
	 * of the players of the blue team in the fight
	 */
	public List<String> getBlueNames() {
		synchronized(log.getBlue()) {
			return new ArrayList<String>(log.getBlue());
		}
	}
	
	public void addFighter(CharacterHandler myCharacter, Team team) {}
	public void removeFighter(CharacterHandler myCharacter) {}
	public void changeCurrentEnemy() {}
	
	public CharacterHandler getCurrentEnemy(CharacterHandler myCharacter) {
		return currentEnemy.get(myCharacter);
	}	
		
	/**
	 * This methods iterates through the map looking for the attacks for a particular character
	 * @param character for whom the attacks need to be found
	 * @return List<AttackKey> of all possible attacks for the character
	 */
	private List<AttackKey> findAttacksFor(CharacterHandler character) {
		List<AttackKey> waitingAttacks = new ArrayList<AttackKey>();
		for(AttackKey key : attacks.keySet()) {
			if(key.getReceiver().equals(character)) {
				waitingAttacks.add(key);
			}
		}
		return waitingAttacks;
	} 
	/**
	 * This method performs an attack of the specified character against his current enemy,
	 * checks if the attacked player is defeated, and if true, ends the fight and performs fight cleanup.
	 * Thread-safe.
	 * @param myCharacter is the character performing an attack
	 * @param attack is the attack of the character
	 * @param block1 is the first block of the character
	 * @param block2 is the second block of the character
	 */
	public synchronized void attack(CharacterHandler myCharacter, String targetName, AttackPlace attack, AttackPlace block1, AttackPlace block2) {		
		// checks if the current enemy corresponds to the enemy attacked. Prevents from accidental attacks on defeated characters.
		if (!getCurrentEnemy(myCharacter).getName().equals(targetName.trim())) {
			return;
		}
		/* this section checks if the character has been scheduled for finishing, and removes him from the list */
		if((this.finishLastRed != null && finishLastRed.equals(myCharacter)) || 
		  (this.finishLastBlue != null && finishLastBlue.equals(myCharacter))) {						
			for(CharacterHandler enemy : getEnemyTeam(myCharacter)) {
				enemy.onReceive(FightMessageFactory.getFightMessageFactory().createFightControlNotFinishMessage(myCharacter));
			}
			if(myCharacter.equals(finishLastRed)) {
				finishLastRed = null;
			} else {
				finishLastBlue = null;
			}		
		}
		
		/* this section performs an actual attack, either responding or dealing damage first, thus, scheduling the attack */
		FightMessage message = new FightMessage();	// message about the result of the current attack
		// creating an attack object 
		Attack hit = new Attack();
		hit.setAttacker(myCharacter);
		hit.setReceiver(getCurrentEnemy(myCharacter));
		hit.setAttack(attack);
		hit.setBlock(block1);
		hit.setSecondBlock(block2);			
		// checking if there is any attack ready for the player 
		AttackKey key = new AttackKey(hit.getAttacker(), hit.getReceiver());
		// swaps the attacker and the receiver 
		key.reverse();	
		if(attacks.containsKey(key)) {	// case the attack is already queued for this character
			Attack response = attacks.remove(key);
			
			if (hit.getAttack() == response.getBlock() || hit.getAttack() == response.getSecondBlock()) {
				message.addAttack(hit.dealDamage(true));
			} else {
				message.addAttack(hit.dealDamage(false));
			}
			checkIfDefeated(hit.getReceiver(), message);
			
			if (response.getAttack() == hit.getBlock() || response.getAttack() == hit.getSecondBlock()) {
				message.addAttack(response.dealDamage(true));
			} else {
				message.addAttack(response.dealDamage(false));
			}	
			checkIfDefeated(response.getReceiver(), message);	
			
			log.addMessage(message);			
			for (CharacterHandler character : fighters) {
				character.onReceive(message);
			}									
			hit.getReceiver().onReceive(FightMessageFactory.getFightMessageFactory().createFightControlAttackableMessage(hit.getAttacker()));
						
		} else {
			key.reverse();
			if(attacks.containsKey(key)) {	// case the attack has already been scheduled for the receiver and waiting for response
				hit.getAttacker().onReceive(FightMessageFactory.getFightMessageFactory().createFightNoAttackMessage(hit.getReceiver()));
				return;
			} else {	// attack is new for the receiver and will be queued for him
				attacks.put(key, hit);	
				// when the receiver gets the first attack in his queue, his timeout timer starts counting
				if(timeouts.get(hit.getReceiver()) == null) {
					timeouts.put(hit.getReceiver(), executor.schedule(new TimeoutEvent(hit.getReceiver()), getTimeout(), TimeUnit.MINUTES));
				}
				hit.getAttacker().onReceive(FightMessageFactory.getFightMessageFactory().createFightControlNotAttackableMessage(hit.getReceiver()));
			}			
		}	
		/* checks if there is any timeout scheduled for the character, and resets it if there are any more attacks waiting */
		if(timeouts.get(myCharacter) != null) {
			timeouts.get(myCharacter).cancel(false); 
			timeouts.remove(myCharacter);			
		}
		if(!findAttacksFor(myCharacter).isEmpty()) {
			timeouts.put(myCharacter, executor.schedule(new TimeoutEvent(myCharacter), getTimeout(), TimeUnit.MINUTES));
		}	
		setNewOpponent(myCharacter);		
		checkFightEnd();
	}	
	/**
	 * Method called when the enemy hero is finished. Alternative to normal attack.
	 * @param myCharacter character to kill instantly
	 */
	public synchronized void finishEnemy(CharacterHandler myCharacter) {
		CharacterHandler finishedCharacter = getEnemyTeam(myCharacter).get(0);
		if(finishedCharacter.equals(finishLastRed) || finishedCharacter.equals(finishLastBlue)) {			
			
			finishedCharacter.setCurrentHp(0);
			
			FightEventMessage event = new FightEventMessage();
			event.setType("finished");
			event.setTarget(finishedCharacter.getName());
			FightMessage message = new FightMessage();
			message.addEvent(event);
			
			checkIfDefeated(finishedCharacter, message);
			log.addMessage(message);
			for (CharacterHandler character : fighters) {
				character.onReceive(message);
			}				
		} else {
			myCharacter.onReceive(FightMessageFactory.getFightMessageFactory().createFightNoFinishMessage());
		}
		checkFightEnd();
	}	
	/**
	 * Finds a new opponent when the player needs to change the enemy
	 * @param character the character who changes the opponent
	 * @param isCompulsory the boolean stating that the opponent change cannot lead to choosing the same opponent
	 */
	private void setNewOpponent(CharacterHandler character) {
		List<CharacterHandler> opponents = getEnemyTeam(character);
		if (opponents.size() > 0 && currentEnemy.containsKey(character)) {
			int newOpponentIndex = (int)(Math.random()*opponents.size());
			CharacterHandler newOpponent = opponents.get(newOpponentIndex);
		
			if(!newOpponent.equals(currentEnemy.get(character))) {
				currentEnemy.put(character, newOpponent);
				character.onReceive(FightMessageFactory.getFightMessageFactory().createFightControlEnemyMessage(newOpponent));
			} else if (opponents.size() > 1) {
				setNewOpponent(character);	// it will recursively look for the enemy which is not the current one
			}
		} 
	}	
	private void checkIfDefeated(CharacterHandler character, FightMessage message) {
		if (character.getState() == CharacterState.DEFEATED) {
			/* remove the attacks destined for or from the defeated character */
			Iterator<AttackKey> iterator = attacks.keySet().iterator();
			while(iterator.hasNext()) {
				AttackKey attack = iterator.next();
				if(attack.getAttacker().equals(character) || attack.getReceiver().equals(character)) {
					iterator.remove();
				}
			}
			/* remove the defeated player timeouts */
			if(timeouts.containsKey(character)) {
				timeouts.remove(character).cancel(false);
			}					
			/* send a defeat message */
			FightEventMessage defeat = new FightEventMessage();
			defeat.setType("defeat");
			defeat.setTarget(character.getName());
			message.addEvent(defeat);
			/* remove defeated character from live characters*/
			if (teamRed.contains(character)) {
				teamRed.remove(character);
				teamRedDefeated.add(character);
			} else {
				teamBlue.remove(character);
				teamBlueDefeated.add(character);
			}		
			/* remove the defeated character from the table of current enemies, substitute new enemies for the remaining players */
			currentEnemy.remove(character);
			for (Map.Entry<CharacterHandler, CharacterHandler> enemyPair : currentEnemy.entrySet()) {
				if(enemyPair.getValue().equals(character)) {
					CharacterHandler opponentSeeker = enemyPair.getKey();
					if(getEnemyTeam(opponentSeeker).size() > 0) {
						setNewOpponent(opponentSeeker);
					}
				}
			}			
		}
	}	
	/**
	 * Checks if any of the teams is empty, thus, deciding if the fight is over
	 */
	private void checkFightEnd() {		
		if(teamRed.isEmpty() || teamBlue.isEmpty()) {			
			FightMessage endMessage = new FightMessage();
			if (teamRed.isEmpty() && !teamBlue.isEmpty()) {			
				endMessage.addFightStateEvent(new FightStateMessage("fight_end", getRedNames(), getBlueNames(), "blue"));
			} else if (!teamRed.isEmpty() && teamBlue.isEmpty()) {				
				endMessage.addFightStateEvent(new FightStateMessage("fight_end", getRedNames(), getBlueNames(), "red"));
			} else if (teamRed.isEmpty() && teamBlue.isEmpty()) {			
				endMessage.addFightStateEvent(new FightStateMessage("fight_end", getRedNames(), getBlueNames(), "draw"));
			}
			log.addMessage(endMessage);
			for (CharacterHandler character : fighters) {
				character.onReceive(endMessage);
			}						
			end();
		}
	}	
	/**
	 * Saves the log to the database, clears all lists, changes the state of fighters and removes all timeouts - cleans up resources
	 */
	private void end() {
		EntityManager em = null;
		try {
			em = SystemResources.getPersistenceContext().createEntityManager();
			em.getTransaction().begin();
			em.persist(log);
			em.getTransaction().commit();
		} catch(Exception ex) {
			em.getTransaction().rollback();
		} finally {
			em.close();
		}		
		for (CharacterHandler character : fighters) {
			character.removeFromCurrentFight();
		}		
		teamRed.clear();
		teamRedDefeated.clear();
		teamBlue.clear();
		teamBlueDefeated.clear();
		fighters.clear();
		attacks.clear();	
		for(Future<?> future : timeouts.values()) {
			future.cancel(false);
		}
		World.getLocation(getLocation()).removeFight(this);	
	}	
		
	class TimeoutEvent implements Runnable {
		
		private CharacterHandler character;
		
		public TimeoutEvent(CharacterHandler character) {
			this.character = character;
		}		
		@Override
		public void run() {
			synchronized(Fight.this) {
				if(character.getState() == CharacterState.FIGHT) {
					FightMessage message = new FightMessage();
					/* retrieve the attacks against the character and deal damage */
					List<AttackKey> waitingAttackKeys = findAttacksFor(character);
					if(!waitingAttackKeys.isEmpty()) {
						AttackKey pendingAttackKey = waitingAttackKeys.get((int)(Math.random()*waitingAttackKeys.size()));
						Attack pendingAttack = attacks.remove(pendingAttackKey);
						AttackMessage attack = pendingAttack.dealDamage(false);
						message.addAttack(attack);
						
						FightEventMessage sleep = new FightEventMessage();
						sleep.setType("sleep");
						sleep.setTarget(character.getName());
						message.addEvent(sleep);
						
						checkIfDefeated(character, message);
						
						log.addMessage(message);	
						for(CharacterHandler character : fighters) {
							character.onReceive(message);
						}						
						
						if (character.getState() != CharacterState.DEFEATED) {
							
							pendingAttack.getAttacker().onReceive(FightMessageFactory.getFightMessageFactory().createFightControlAttackableMessage(pendingAttack.getReceiver()));	
							
							if(getCharacterTeam(character).size() == 1) {
								if(teamRed.contains(character)) {
									finishLastRed = character;
								} else {
									finishLastBlue = character;
								}
								
								for(CharacterHandler enemy : getEnemyTeam(character)) {
									enemy.onReceive(FightMessageFactory.getFightMessageFactory().createFightControlFinishMessage(character));
								}							
							}						
							if (!findAttacksFor(character).isEmpty()) {
								timeouts.remove(character);
								timeouts.put(character, executor.schedule(new TimeoutEvent(character), getTimeout(), TimeUnit.MINUTES));
							} else {
								timeouts.remove(character);
							}
						}								
					}	
				}	// end of defeated check
				checkFightEnd();
			} // end of synchronized block 
		}	
	} // end of Timeout inner class
	
	/** 
	 * Returns a list of fight messages for a given page.
	 * @param page index to return. In case it is 0 - the last page is returned. 
	 */
	public synchronized List<FightMessage> getMessageList(int page) {
		List<FightMessage> messages = new ArrayList<FightMessage>();
		if (page == 0) {
			page = log.getMessage().get(log.getMessage().size()-1).getPage();
		}
		ListIterator<FightMessage> it = log.getMessage().listIterator(log.getMessage().size());
		while(it.hasPrevious()) {
			FightMessage message = it.previous();
			if (page == message.getPage()) {
				messages.add(0, message);
			}
		}
		return messages;
	}
	
}