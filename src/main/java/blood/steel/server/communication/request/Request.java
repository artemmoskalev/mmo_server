package blood.steel.server.communication.request;

import java.util.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import blood.steel.server.controllers.GameSession;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
	
	@XmlTransient
	private GameSession session;
	
	public GameSession getSession() {
		return session;
	}
	public void setSession(GameSession session) {
		this.session = session;
	}	
	
	@XmlAttribute
	private String command;
	@XmlJavaTypeAdapter(RequestAdapter.class)
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
