package connect.bot.generators;

public class Generator {

	private static String[] gender;
	private static String[] race;
	private static String[] location;
	
	static {
		gender = new String[] {"male", "female"};
		race = new String[] {"elf", "orc", "undead", "human"};
		location = new String[] {"trainingroom", "castle", "barracks"};
	}
	
	public static String generateRace() {
		int pointer = (int)(Math.random()*race.length);
		return race[pointer];
	}
	public static String generateGender() {
		int pointer = (int)(Math.random()*gender.length);
		return gender[pointer];
	}
	public static String generateLocation() {
		int pointer = (int)(Math.random()*location.length);
		return location[pointer];
	}
	
}
