package blood.steel.server.codec;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXB;
import io.netty.channel.*;
import io.netty.handler.codec.*;

import blood.steel.server.communication.messaging.SystemMessageFactory;
import blood.steel.server.communication.request.Request;
import blood.steel.server.controllers.GameSession;

public class StringToRequestDecoder extends MessageToMessageDecoder<String> {
	
	private GameSession session;
	
	public StringToRequestDecoder(GameSession session) {
		this.session = session;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, String command,
			List<Object> out) throws Exception {		
		command = command.trim();
		if(command.equalsIgnoreCase("HEARTBEAT")) {
			return;
		} else {
			StringReader commandReader = new StringReader(command);		
			Request request = JAXB.unmarshal(commandReader, Request.class);	
			request.setSession(this.session);
			commandReader.close();	
			out.add(request);
		}
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {	
		session.getGameOutputStream().write(SystemMessageFactory.INVALID);
		session.getGameOutputStream().write(SystemMessageFactory.SOCKET_SHUTDOWN);
    }

}
