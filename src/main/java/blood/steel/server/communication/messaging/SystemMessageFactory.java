package blood.steel.server.communication.messaging;

public class SystemMessageFactory {

	public final static Message SOCKET_SHUTDOWN = new SystemMessage(0, "Socket terminated!");
	public final static Message INVALID = new SystemMessage(1, "Invalid Message Structure!");
	public final static Message COMMAND_MISPLACE = new SystemMessage(2, "This command could not be issued at this point!");
	public final static Message MISSING_PARAMETER = new SystemMessage(3, "There are missing parameters for the command!");
	public final static Message PARAMETER_TYPE_NOT_FOUND = new SystemMessage(4, "The parameter type does not exist!");
	
	private SystemMessageFactory() {}
	
}
