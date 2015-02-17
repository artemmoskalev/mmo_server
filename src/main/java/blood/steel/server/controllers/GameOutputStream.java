package blood.steel.server.controllers;

import io.netty.channel.*;

import blood.steel.server.communication.messaging.*;

public class GameOutputStream {

	private Channel stream;
	
	public GameOutputStream(Channel stream) {
		this.stream = stream;
	}
	
	public void write(Message message) {
		if (message.getCode() == 0) {			
			stream.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE);
		} else {
			stream.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
		}
	}	
		
}
