package connect.bot;

import java.io.*;
import java.net.*;
import java.util.UUID;

import connect.bot.request.*;

public class Bot {

	private String name;
	private String password;
	
	private Socket socket;
	private OutputStreamWriter writer;
	private BufferedReader br;
	
	public Bot() {
		setName(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
		setPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	private synchronized void write(String request) {
		try {
			writer.write(request);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	
	public void connect(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			writer = new OutputStreamWriter(socket.getOutputStream());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(new InputReader()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendBotRequest(BotRequest request) {
		write(Producer.produce(request));
	}
	
	public void disconnect() {
		try {
			br.close();
			socket = null;
			writer = null;
			br = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class InputReader implements Runnable {
		public void run() {
			String result = null;
			try {
				while((result = br.readLine()) != null) {
					if(result.equals("HEARTBEAT")) {
						write("HEARTBEAT\n");
					} else if (result.equals("<code>0</code>")) {
						disconnect();
						System.out.println("Disconnected!");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
}
