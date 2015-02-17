package blood.steel.server;

import java.util.logging.*;

import blood.steel.server.codec.*;
import blood.steel.server.controllers.*;

import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.*;
import io.netty.channel.socket.nio.*;
import io.netty.handler.timeout.IdleStateHandler;

public class Server {

	ServerBootstrap bootstrap;
	private final static Integer HEARTBEAT_INTERVAL = 300;
	
	public Server(int port) throws Exception {
		bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup())
				 .channel(NioServerSocketChannel.class)
				 .localAddress(port)
				 .childHandler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel newChannel)
							throws Exception {
																	
						/* creating output stream and setting it to the session*/
						GameOutputStream out = new GameOutputStream(newChannel);
						
						GameSession session = new GameSession();
						session.setGameOutputStream(out);
						
						// input handlers
						newChannel.pipeline().addLast(new ByteToStringDecoder());
						/* setting only to this decoder, because it will put the session into the decoded request */
						newChannel.pipeline().addLast(new StringToRequestDecoder(session)); 
						newChannel.pipeline().addLast(new RequestProcessor());
						
						// output handlers
						newChannel.pipeline().addFirst(new ResponseToStringEncoder());
						newChannel.pipeline().addFirst(new StringToByteEncoder());		
						
						// handlers that check if the connection has been idle for too long
						newChannel.pipeline().addFirst(new IdleChannelDisconnector(session));
						newChannel.pipeline().addFirst(new IdleStateHandler(HEARTBEAT_INTERVAL, 0, 0));
						
					}
				 });
		
		Logger log = Logger.getLogger("server");		
		Handler logfile = new FileHandler("serverlog.txt");
		log.addHandler(logfile);
		
	}
	
	public void startServer() throws Exception {
		ChannelFuture server = bootstrap.bind().sync();
		System.out.println("Blood and Steel Server is listening on port " + server.channel().localAddress());
		Logger.getLogger("server").log(Level.INFO, "Server is listening on port "+ server.channel().localAddress());
	}	
	
	public void stopServer() throws Exception {
		System.out.println("Blood and Steel Server Shutdown initiated!");
		bootstrap.childGroup().shutdownGracefully().sync();
		Logger.getLogger("server").log(Level.INFO, "Server is shutdown!");
	}
	
}
