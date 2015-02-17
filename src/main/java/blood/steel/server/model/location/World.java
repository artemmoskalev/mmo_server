package blood.steel.server.model.location;

import java.util.*;
import java.util.concurrent.*;

public class World {
	//singleton data
	private static World city;
	private static Map<LocationType, Location> locations;	
	
	static {
		city = new World();				
		locations = new ConcurrentHashMap<LocationType, Location>();		
		// these must be always in pairs
		locations.put(LocationType.TRAININGROOM, new TrainingRoom());
		locations.put(LocationType.CASTLE, new Castle());
		locations.put(LocationType.BARRACKS, new Barracks());		
		locations.put(LocationType.WEAPONSHOP, new WeaponShop());
	}
	public static World getInstance() {
		return city;
	}	
	public static Location getLocation(LocationType name) {
		return locations.get(name);
	}
	
	// private constructor
	private World() {
		
	}	
}
