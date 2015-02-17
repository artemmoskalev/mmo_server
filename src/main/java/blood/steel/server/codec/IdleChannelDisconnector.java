package blood.steel.server.codec;

import java.util.logging.*;

import blood.steel.server.controllers.GameSession;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.timeout.*;
import io.netty.util.CharsetUtil;

public class IdleChannelDisconnector extends ChannelDuplexHandler {
	
	private final static ByteBuf HEARTBEAT =
								Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HEARTBEAT\n", CharsetUtil.UTF_8));
	
	private boolean pendingClose = false;
	private GameSession session = null;
	
	public IdleChannelDisconnector(GameSession session) {
		this.session = session;
	}
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) event;
            if (idleStateEvent.state() == IdleState.READER_IDLE && pendingClose) {
            	ctx.channel().close();
            } else if(idleStateEvent.state() == IdleState.READER_IDLE && !pendingClose) {
            	pendingClose = true;
            	ctx.writeAndFlush(HEARTBEAT.duplicate()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } 
    }
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object message) {
		this.pendingClose = false;
		ctx.fireChannelRead(message);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		try {
			session.finalizeSession();
			ctx.channel().close().sync();			
		} catch (InterruptedException cause) {
			Logger.getLogger("server").log(Level.SEVERE, "Was not able to close the channel!", cause);
		}
	}
	
}
