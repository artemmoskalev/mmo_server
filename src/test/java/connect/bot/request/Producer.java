package connect.bot.request;

import java.util.*;

public class Producer {

	public static String produce(BotRequest request) {
		if(request.getCommand() == null) {
			throw new RuntimeException("No command specified!");
		}
		String result = "<request command=\"" + request.getCommand() + "\"><parameters>";
		for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
			result += "<parameter name=\"" + entry.getKey() + "\" value=\"" + entry.getValue() + "\" />";
		}
		result += "</parameters></request>\n";
		return result;
	}
	
}
