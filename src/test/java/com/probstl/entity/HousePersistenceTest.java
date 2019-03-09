package com.probstl.entity;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.RegisterExtension;

@TestInstance(Lifecycle.PER_CLASS)
public class HousePersistenceTest {

	@RegisterExtension
	PersistenceExtension extension = PersistenceExtension.initPersistence("integration-test");

	@AfterAll
	public void closeUp() {
		extension.closeUp();
	}

	@Test
	public void persistHouse() {
		House hexe = new House("Hexenhaeusl");
		extension.em().persist(hexe);

		List<House> resultList = extension.em().createQuery("SELECT c FROM House c", House.class).getResultList();

		Assertions.assertEquals(1, resultList.size());

		for (House homes : resultList) {
			Assertions.assertNotNull(homes.getId());
		}

	}
}
