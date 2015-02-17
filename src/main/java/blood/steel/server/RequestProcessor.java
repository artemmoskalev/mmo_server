package blood.steel.server;

import java.util.logging.*;

import io.netty.channel.*;

import blood.steel.server.communication.request.*;
import blood.steel.server.controllers.RequestDispatcher;

public class RequestProcessor extends SimpleChannelInboundHandler<Request> {

	RequestDispatcher dispatcher = RequestDispatcher.newInstance();
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request request)
			throws Exception {	
		if (RequestFilter.isValid(request)) {
			dispatcher.sendRequest(request);
		}		
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {		
		Logger.getLogger("server").log(Level.SEVERE, "Request Processing Error Occured!", cause);
		ctx.channel().close();
    }

}
