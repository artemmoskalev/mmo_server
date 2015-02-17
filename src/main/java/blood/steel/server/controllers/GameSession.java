package blood.steel.server.controllers;

import java.util.UUID;

import javax.persistence.*;

import blood.steel.server.SystemResources;
import blood.steel.server.authentication.*;

public class GameSession {

	private String sessionId;

	private GameOutputStream gameOutputStream;
	private CharacterController characterController;
	private Player player;
	
	public GameSession() {
		/* creating a pseudo random number and acquiring session with it */
		this.sessionId = UUID.randomUUID().toString();
		player = new Player();
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String newSessionId) {		
		this.sessionId = newSessionId;
	}
	
	public boolean isUnknown() {
		return (this.player.getState() == PlayerState.UNKNOWN);
	}
	public boolean isLogged() {
		return (this.player.getState() == PlayerState.LOGGED);
	}
	public boolean isRegistering() {
		return (this.player.getState() == PlayerState.REGISTERING);
	}
		
	public void setGameOutputStream(GameOutputStream gameOutputStream) {
		this.gameOutputStream = gameOutputStream;
	}
	public GameOutputStream getGameOutputStream() {
		return this.gameOutputStream;
	}
	public void setCharacterController(CharacterController characterController) {
		this.characterController = characterController;
	}
	public CharacterController getCharacterController() {
		return this.characterController;
	}	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/* this method cleans up the game resources if any at the disconnection */
	public void finalizeSession() {
		if (isRegistering()) { // remove waiting state if exists
			player.setState(PlayerState.UNKNOWN);
			EntityManager em = null;
			try {
				em = SystemResources.getPersistenceContext().createEntityManager();	
				EntityTransaction tx = em.getTransaction();
				tx.begin();
				Player tempPlayer = em.merge(player);
				em.remove(tempPlayer);
				tx.commit();
			} finally {
				em.close();
			}
		}
		if(isLogged()) {
			getCharacterController().cleanup();
		}
	}
	
}
