package connect.bot.request;

import java.util.*;

public class BotRequest {
	
	private String command;
	private Map<String, String> parameters = new HashMap<String, String>();
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Map<String, String> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}	
		
}
