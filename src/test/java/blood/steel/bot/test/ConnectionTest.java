package blood.steel.bot.test;

import connect.bot.Bot;
import connect.bot.generators.Generator;
import connect.bot.request.BotRequest;

public class ConnectionTest {
	
	private Bot[] bots;
	
	public void performTest() throws Exception {
			
		bots = new Bot[500];
		for (int i = 0; i < bots.length; i++) {
			bots[i] = new Bot();
		}
		for (int i = 0; i < bots.length; i++) {
			BotRequest registerRequest = new BotRequest();
			registerRequest.setCommand("register");
			registerRequest.getParameters().put("login", bots[i].getName());
			registerRequest.getParameters().put("password", bots[i].getPassword());
			registerRequest.getParameters().put("email", bots[i].getName());
			
			bots[i].connect("localhost", 2012);
			bots[i].sendBotRequest(registerRequest);
			
			BotRequest createRequest = new BotRequest();
			createRequest.setCommand("create");
			createRequest.getParameters().put("type", Generator.generateRace());
			createRequest.getParameters().put("gender", Generator.generateGender());
			
			bots[i].sendBotRequest(createRequest);
			Thread.sleep(50);
		}
		
		
		try {
			System.out.println("registration complete!");
			for (int i = 120; i > 0; i--) {
				System.out.println(i);
				Thread.sleep(1000);
			}		
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 50; i++) {
			new Thread(new Mover()).start();
		}
		
		try {
			System.out.println("move is on!");
			Thread.sleep(120000);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < bots.length; i++) {
			bots[i].disconnect();
		}
		
	}
	
	class Mover implements Runnable {
		@Override
		public void run() {
			int i = 0;
			while (i < 100) {				
				BotRequest moveRequest = new BotRequest();
				moveRequest.setCommand("character_move_to");
				moveRequest.getParameters().put("location", Generator.generateLocation());
				
				int pointer = (int)(Math.random()*bots.length);
				bots[pointer].sendBotRequest(moveRequest);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
			}
		}
	}
	
}
