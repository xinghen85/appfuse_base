package com.btxy.basis.common;

import org.mongodb.morphia.Datastore;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringContext {

	private SpringContext() {
	};


	protected static ApplicationContext content = null;

	public static void setApplicationContext(ApplicationContext ac1) {
		content=ac1;
	}
	
	public static ApplicationContext getApplicationContext() {
		return content;
	}
	protected static JdbcTemplate jdbcTemplate = null;
	
	static public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) content.getBean("jdbcTemplate");
		}
		return jdbcTemplate;
	}

	
	
	private static Datastore mongoDbDatastore=null;
	
	static public Datastore getDatastore() {
		if (mongoDbDatastore == null) {
			mongoDbDatastore = (Datastore) content.getBean("datastore");
		}
		return mongoDbDatastore;
	}
	
}
