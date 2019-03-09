package com.probstl.entity;

import java.util.Iterator;
import java.util.List;

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
		
	}


}
