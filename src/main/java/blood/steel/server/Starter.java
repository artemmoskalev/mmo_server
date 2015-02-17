package blood.steel.server;

import java.io.*;

public class Starter {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("You should specify the port to run on!");
			return;
		} else {
			try {				
				Server server = new Server(Integer.valueOf(args[0]));
				server.startServer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				
				String serverCommand = null;
				while ((serverCommand = reader.readLine()) != null) {
					if(serverCommand.equals("quit")) {
						server.stopServer();
						reader.close();
						break;
					}
				}				
			} catch (NumberFormatException e) {
				System.err.println("The first argument must be a port number digit!");
			}catch (Exception e) {
				e.printStackTrace();				
			} 
		}				
	}
}
