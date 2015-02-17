package blood.steel.server.model.util;

import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.*;

import blood.steel.server.SystemResources;

public class ItemSequenceGenerator {

	private static AtomicLong id;
	
	static {
		EntityManager em = null;
		Long generator = 1L;		
		try {				
			em = SystemResources.getPersistenceContext().createEntityManager();
			TypedQuery<Long> query = em.createQuery("SELECT max(i.id) FROM Item i", Long.class);
			generator = query.getSingleResult();			
			if(generator != null) {
				++generator;
			} else {
				generator = 1L;
			}
		} finally {
			em.close();			
		}
		id = new AtomicLong(generator);			
	}		
	
	public static long getNewItemId() {
		return id.getAndIncrement();
	}
	
}
