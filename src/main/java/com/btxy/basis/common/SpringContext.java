package com.btxy.basis.common;

import javax.servlet.ServletContext;

import org.mongodb.morphia.Datastore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.mongodb.DB;

public class SpringContext {

	public static final String[] CONFIG_LOCATIONS = new String[] { "applicationContext-resources.xml", "applicationContext-service.xml",
		"applicationContext-dao.xml"
		,"applicationContext-couchbase.xml" 
		};

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
		if (content == null) {
			content = new ClassPathXmlApplicationContext(CONFIG_LOCATIONS);
		}
		return content;
	}
	private static JedisPool jedisPool=null;
	protected static JdbcTemplate jdbcTemplate = null;
	protected static JdbcTemplate jdbcTemplateForBsquiz = null;
	
	
	
	static public JedisPool getJedisPool() {
		if (jedisPool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxActive(100);
	        config.setMaxIdle(10);
	        config.setMaxWait(1000l);
	        
	        config.setTestOnBorrow(false);
	        

	       //lyz jedisPool = new JedisPool(config, "10.150.146.120", 6379);
			//jedisPool = (JdbcTemplate) SpringContext.getApplicationContext().getBean("jdbcTemplate");
		}
		return jedisPool;
	}

	
	static public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = (JdbcTemplate) SpringContext.getApplicationContext().getBean("jdbcTemplate");
		}
		return jdbcTemplate;
	}

	static public JdbcTemplate getJdbcTemplateForBsquiz() {
		if (jdbcTemplateForBsquiz == null) {
			jdbcTemplateForBsquiz = (JdbcTemplate) SpringContext.getApplicationContext().getBean("jdbcTemplateForBsquiz");
		}
		return jdbcTemplateForBsquiz;
	}
	
	
	
	
	private static Datastore mongoDbDatastore=null;
	
	static public Datastore getDatastore() {
		if (mongoDbDatastore == null) {
			mongoDbDatastore = (Datastore) SpringContext.getApplicationContext().getBean("datastore");
		}
		return mongoDbDatastore;
	}
	
	
	private static DB dbForLog=null;
	
	static public DB getDbForLog() {
		if (dbForLog == null) {
			dbForLog = (DB) SpringContext.getApplicationContext().getBean("dbForLog");
		}
		return dbForLog;
		//return mongoClient;
	}
}
