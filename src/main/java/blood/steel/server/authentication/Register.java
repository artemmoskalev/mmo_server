package blood.steel.server.authentication;

import java.util.*;
import javax.persistence.*;

import blood.steel.server.SystemResources;
import blood.steel.server.communication.messaging.*;
import blood.steel.server.controllers.GameOutputStream;
import blood.steel.server.model.util.GameContext;

public class Register {
	
	public static void register(Player player, String command, Map<String, String> parameters, GameOutputStream response) {				
		SystemMessage message = new SystemMessage();
		EntityManager em = null;
		try {
			em = SystemResources.getPersistenceContext().createEntityManager();		
			if (em.find(Player.class, parameters.get("login")) == null) {
				Query query = em.createQuery("SELECT p FROM Player p WHERE p.email = :email").setParameter("email", parameters.get("email"));
				if (!query.getResultList().isEmpty()) {
					message.setCode(12);
					message.setText("Your email already exists in the system!");
					response.write(message);
					response.write(SystemMessageFactory.SOCKET_SHUTDOWN);
				} else {	
					player.setLogin(parameters.get("login"));
					player.setPassword(parameters.get("password"));
					player.setEmail(parameters.get("email"));
					EntityTransaction tx = em.getTransaction();
					tx.begin();		
					em.persist(player);
					tx.commit();
					response.write(new SystemMessage(10, "You have been registered successfully!"));	
					player.setState(PlayerState.REGISTERING);
				}
			} else {
				message.setCode(11);
				message.setText("You could not be registered! There is another player with the chosen name.");
				response.write(message);		
				response.write(SystemMessageFactory.SOCKET_SHUTDOWN);			
			}
		} finally {
			em.close();
		}
	}

	public static void login(Player player, String command, Map<String, String> parameters, GameOutputStream response) {		
		SystemMessage message = new SystemMessage();
		EntityManager em = null;
		try {
			em = SystemResources.getPersistenceContext().createEntityManager();	
			String login = parameters.get("login");
			String password = parameters.get("password");	
			TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p WHERE p.login = :login AND p.password = :password", Player.class);
			query.setParameter("login", login);
			query.setParameter("password", password);
			List<Player> players = query.getResultList();
			if (players.size() == 1) {			
				if (!GameContext.findCharacter(login).isOnline()) {		// checks if player is not online, so he can log in
					player.setLogin(players.get(0).getLogin());
					player.setEmail(players.get(0).getEmail());
					player.setPassword(players.get(0).getPassword());
					player.setState(PlayerState.LOGGED);
					message.setCode(13);
					message.setText("You have logged in!");	
					response.write(message);	
				} else {
					message.setCode(15);
					message.setText("This player is already in the game!");	
					response.write(message);
					response.write(SystemMessageFactory.SOCKET_SHUTDOWN);
				}
			} else {
				message.setCode(14);
				message.setText("Could not login! Either login or password are wrong.");
				response.write(message);		
				response.write(SystemMessageFactory.SOCKET_SHUTDOWN);
			}
		} finally {
			em.close();
		}
	}
	
}
