package blood.steel.server.codec;

import java.util.logging.Level;
import java.util.logging.Logger;

import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

public class ByteToStringDecoder extends LineBasedFrameDecoder {

	private final static int MAX_REQUEST_LENGTH = 1024;
	
	public ByteToStringDecoder() {
		super(MAX_REQUEST_LENGTH);	
	}
		
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) {
		ByteBuf frame = null;
		try {
			frame = (ByteBuf) super.decode(ctx, buffer);
		} catch (Exception e) {
			Logger.getLogger("server").log(Level.SEVERE, "Request Frame parsing Exception!", e);
		}
		if(frame == null) {
			return null;
		} else {
			int bytesToRead = frame.readableBytes();
			byte[] requestBytes = new byte[bytesToRead];
			frame.readBytes(requestBytes);
			return new String(requestBytes);
		}		
	}
	

}
