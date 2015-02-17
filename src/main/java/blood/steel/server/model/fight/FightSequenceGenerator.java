package blood.steel.server.model.fight;

import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import blood.steel.server.SystemResources;

public class FightSequenceGenerator {

	private static FightSequenceGenerator producer = new FightSequenceGenerator();	
	private AtomicLong id;
	
	private FightSequenceGenerator() {
		EntityManager em = null;
		Long generator = 0L;		
		try {				
			em = SystemResources.getPersistenceContext().createEntityManager();
			TypedQuery<Long> query = em.createQuery("SELECT max(f.id) FROM FightLog f", Long.class);
			generator = query.getSingleResult();			
			if(generator != null) {
				++generator;
			} else {
				generator = 0L;
			}
		} finally {
			em.close();			
		}
		id = new AtomicLong(generator);			
	}
	
	public long getNewFightId() {
		return this.id.getAndIncrement();
	}	
	public static FightSequenceGenerator getInstance() {
		return producer;
	}
		
}
