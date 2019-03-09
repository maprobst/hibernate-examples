package com.probstl.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceExtension implements BeforeEachCallback, AfterEachCallback {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceExtension.class);

	private EntityManager em;
	private EntityTransaction tx;
	private EntityManagerFactory emf;
	
	public static PersistenceExtension initPersistence(String persistenceUnit) {
		return new PersistenceExtension(persistenceUnit);
	}
	
	private PersistenceExtension(String persistenceUnit) {
		LOGGER.debug("Create EntityManger, Transaction with persistenceUnit: " + persistenceUnit);
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		this.em = emf.createEntityManager();
		this.tx = em.getTransaction();
	}
	
	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		LOGGER.debug("AfterEach TestExectuion: " + context.getDisplayName());
		this.tx.commit();
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		LOGGER.debug("BeforeEach TestExectuion: " + context.getDisplayName());
		this.tx.begin();
	}
	
	public EntityManager em() {
		return em;
	}
	
	public void closeUp() {
		emf.close();
	}

}
