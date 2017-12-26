package com.iebm.pfds.commons.util;

import javax.persistence.EntityManager;

public class JpaUtils {

	private JpaUtils() {}
	
	private static EntityManager entityManager;

	public static EntityManager getEntityManager() {
		return entityManager;
	}

	public static void setEntityManager(EntityManager input) {
		if(entityManager == null)
			entityManager = input;
	}
	
	
}
