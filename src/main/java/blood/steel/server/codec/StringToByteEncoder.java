package blood.steel.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class StringToByteEncoder extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String response, ByteBuf buffer)
			throws Exception {
		if (!response.endsWith("\n")) {
			response = response + "\n";
		}
		buffer.writeBytes(response.getBytes("UTF-8"));
	}

}
