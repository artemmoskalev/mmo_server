package blood.steel.server;

import javax.persistence.*;

public class SystemResources {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("server");	
	
	public static EntityManagerFactory getPersistenceContext() {
		return emf;
	}	
		
	private SystemResources() {}		
	
}
