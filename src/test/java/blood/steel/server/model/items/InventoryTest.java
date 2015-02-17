package blood.steel.server.model.items;

import org.junit.Test;

import blood.steel.server.model.character.*;
import blood.steel.server.model.location.LocationType;
import blood.steel.server.model.util.*;

public class InventoryTest {

	@Test
	public void testInventory() {
		//CharacterFactory.createCharacter(CharacterType.ELF, "SuperELf", Sex.FEMALE);
		CharacterHandler character = GameContext.findCharacter("SuperElf");
		character.setOnline(new DummyListener());
		
		/*
		Item sword = WeaponFactory.createWeapon("Old Sword");
		Item axe = WeaponFactory.createWeapon("Broken Axe");
		character.addItem(sword);
		character.addItem(axe);
		*/
		//EntityManager em = SystemResources.getPersistenceContext().createEntityManager();
		//em.getTransaction().begin();
		
		//em.persist(item);
		//em.getTransaction().commit();
		
		character.setOffline();
	} 
	
}
