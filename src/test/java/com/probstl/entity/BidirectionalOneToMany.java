package com.probstl.entity;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.RegisterExtension;

@TestInstance(Lifecycle.PER_CLASS)
public class BidirectionalOneToMany {
	
	@RegisterExtension
	PersistenceExtension extension = PersistenceExtension.initPersistence("integration-test");

	@AfterAll
	public void closeUp() {
		extension.closeUp();
	}
	
	@Test
	public void createBuilding() {
		Building building = new Building("haus");
		
		extension.em().persist(building);
	}
	
	@Test
	public void createRoom() {
		Room room = new Room("Bad");
		
		extension.em().persist(room);
		
		List<Building> buildings = extension.em().createQuery("Select b from Building b", Building.class).getResultList();
		for (Building building : buildings) {
			Assertions.assertNotNull(building);
			Assertions.assertNotNull(building.getRooms());
			Assertions.assertEquals(0, building.getRooms().size());
		}
	}
	
	@Test
	public void createBuildingAndRoom() {
		Building building = new Building("Schuppen");
		extension.em().persist(building);
		
		Room room = new Room("Kitchen");
		
		extension.em().persist(room);
		
		extension.em().clear();
		
		Session s = extension.em().unwrap(Session.class);
		Room rloaded = s.bySimpleNaturalId(Room.class).load("Kitchen");
		Assertions.assertNotNull(rloaded);
		
		Building loadNix = s.bySimpleNaturalId(Building.class).load("Sch");
		Assertions.assertNull(loadNix);
		
		Building loadBuilding = s.bySimpleNaturalId(Building.class).load("Schuppen");
		Assertions.assertNotNull(loadBuilding);
		Assertions.assertEquals(0, loadBuilding.getRooms().size());
		
	}
	
	
	@Test
	public void createBuildingAddToList() {
		Building building = new Building("Bude");
		Room room = new Room("Living");
		building.getRooms().add(room);
		extension.em().persist(room);
		extension.em().persist(building);
				
		extension.getTx().commit();
		extension.em().close();
		
		EntityManager em = extension.getEmf().createEntityManager();
		em.getTransaction().begin();
		
		Session s = em.unwrap(Session.class);

		
		Building loadBuilding = s.bySimpleNaturalId(Building.class).load("Bude");
		Assertions.assertNotNull(loadBuilding);
	//	Assertions.assertEquals(0, loadBuilding.getRooms().size());
		
		Room rloaded = s.bySimpleNaturalId(Room.class).load("Living");
		//Assertions.assertNotNull(rloaded);
		em.getTransaction().commit();
		em.close();
	}


}
