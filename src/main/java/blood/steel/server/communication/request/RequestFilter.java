package blood.steel.server.communication.request;

import java.util.*;

import blood.steel.server.communication.messaging.*;
import blood.steel.server.model.location.LocationType;

public class RequestFilter {

	private static Map<String, Map<String, List<String>>> commandParameters;	
	
	static {
		commandParameters = new HashMap<String, Map<String, List<String>>>();
		
		Map<String, List<String>> register = new HashMap<String, List<String>>();		
		register.put("login", null);
		register.put("password", null);
		register.put("email", null);
		commandParameters.put("register", register);
		
		Map<String, List<String>> create = new HashMap<String, List<String>>();
		create.put("type", Arrays.asList("elf", "undead", "orc", "human"));
		create.put("gender", Arrays.asList("male", "female"));
		commandParameters.put("create", create);
		
		Map<String, List<String>> login = new HashMap<String, List<String>>();
		login.put("login", null);
		login.put("password", null);
		commandParameters.put("login", login);
				
		Map<String, List<String>> charactermoveto = new HashMap<String, List<String>>();
		List<String> locationTypeNames = new LinkedList<String>();
		for (LocationType locationType : LocationType.values()) {
			locationTypeNames.add(locationType.toString().toLowerCase());
		}
		charactermoveto.put("location", locationTypeNames);		
		commandParameters.put("character_move_to", charactermoveto);		
		
		Map<String, List<String>> locationRegisterDuelApplication = new HashMap<String, List<String>>();
		locationRegisterDuelApplication.put("duration", Arrays.asList("5", "10", "30"));	
		locationRegisterDuelApplication.put("timeout", Arrays.asList("1", "3", "5"));		
		commandParameters.put("location_register_duel_application", locationRegisterDuelApplication);	
		
		Map<String, List<String>> locationAddToDuelApplication = new HashMap<String, List<String>>();
		locationAddToDuelApplication.put("owner", null);		
		commandParameters.put("location_add_to_duel_application", locationAddToDuelApplication);		
		
		Map<String, List<String>> fightAttack = new HashMap<String, List<String>>();
		fightAttack.put("target", null);
		fightAttack.put("attack", Arrays.asList("head", "chest", "abdomen", "legs"));	
		fightAttack.put("block1", Arrays.asList("head", "chest", "abdomen", "legs"));		
		fightAttack.put("block2", Arrays.asList("head", "chest", "abdomen", "legs"));	
		commandParameters.put("fight_attack", fightAttack);	
		
		Map<String, List<String>> buyItem = new HashMap<String, List<String>>();
		buyItem.put("name", null);
		commandParameters.put("location_buy_item", buyItem);
		
		Map<String, List<String>> listItems = new HashMap<String, List<String>>();
		listItems.put("type", null);
		listItems.put("level", null);
		commandParameters.put("location_list_items", listItems);
		
		Map<String, List<String>> characterUseItem = new HashMap<String, List<String>>();
		characterUseItem.put("id", null);
		commandParameters.put("character_use_item", characterUseItem);
		
	}
	
	public static boolean isValid(Request request) {
		boolean valid = true;		
		if (commandParameters.containsKey(request.getCommand())) {
			Map<String, List<String>> parameters = commandParameters.get(request.getCommand());
			for (String parameter : parameters.keySet()) {
				if (!request.getParameters().containsKey(parameter)) {
					valid = false;
					request.getSession().getGameOutputStream().write(SystemMessageFactory.MISSING_PARAMETER);
					request.getSession().getGameOutputStream().write(SystemMessageFactory.SOCKET_SHUTDOWN);
					break;
				} else if(parameters.get(parameter) != null && 
								!parameters.get(parameter).contains(request.getParameters().get(parameter))) {
					valid = false;
					request.getSession().getGameOutputStream().write(SystemMessageFactory.PARAMETER_TYPE_NOT_FOUND);
					request.getSession().getGameOutputStream().write(SystemMessageFactory.SOCKET_SHUTDOWN);
					break;							
				}
			}			
		}		
		return valid;
	}	
	
}
