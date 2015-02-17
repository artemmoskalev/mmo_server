package blood.steel.server.codec;

import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXB;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import blood.steel.server.communication.messaging.*;

public class ResponseToStringEncoder extends MessageToMessageEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message response, List<Object> out) throws Exception {		
		StringWriter writer = new StringWriter();
		JAXB.marshal(response, writer);
		String result = writer.toString().replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n", "");
		out.add(result);
		writer.close();		
	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Logger.getLogger("server").log(Level.SEVERE, "Invalid Message Format", cause);
		ctx.channel().close();
    }
	
}
