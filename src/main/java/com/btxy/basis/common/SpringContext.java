package com.btxy.basis.common;

import javax.servlet.ServletContext;

import org.mongodb.morphia.Datastore;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringContext {

	private SpringContext() {
	};

	static public void setSpringContext(ApplicationContext _content) {
		content = _content;
	}
	static public void setServletContext(ServletContext _content) {
		servletContext = _content;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

	protected static ApplicationContext content = null;
	protected static ServletContext servletContext = null;

	public static void setApplicationContext(ApplicationContext ac1) {
		content=ac1;
	}
	
	public static ApplicationContext getApplicationContext() {
		return content;
	}
	protected static JdbcTemplate jdbcTemplate = null;
	protected static JdbcTemplate jdbcTemplateForBsquiz = null;
	
	
	
	
	static public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContext.getApplicationContext().getBean("jdbcTemplate");
		}
		return jdbcTemplate;
	}

	
	
	private static Datastore mongoDbDatastore=null;
	
	static public Datastore getDatastore() {
		if (mongoDbDatastore == null) {
			mongoDbDatastore = (Datastore) SpringContext.getApplicationContext().getBean("datastore");
		}
		return mongoDbDatastore;
	}
	
}
