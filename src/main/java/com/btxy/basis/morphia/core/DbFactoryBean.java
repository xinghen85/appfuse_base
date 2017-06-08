package com.btxy.basis.morphia.core;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;




public class DbFactoryBean extends AbstractFactoryBean<DB> {
	            
	 private MongoClient mongoClient;    //mongo实例，最好是单例                                                            
	 private String dbName;    //数据库名                                                                       
	 private String username;    //用户名，可为空                                                               
	 private String password;    //密码，可为空                                                                 
	 private boolean toEnsureIndexes=false;    //是否确认索引存在，默认false                                    
	 private boolean toEnsureCaps=false;    //是否确认caps存在，默认false                                       
	                                                                                                            


	
	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isToEnsureIndexes() {
		return toEnsureIndexes;
	}

	public void setToEnsureIndexes(boolean toEnsureIndexes) {
		this.toEnsureIndexes = toEnsureIndexes;
	}

	public boolean isToEnsureCaps() {
		return toEnsureCaps;
	}

	public void setToEnsureCaps(boolean toEnsureCaps) {
		this.toEnsureCaps = toEnsureCaps;
	}

	@Override                                                                                                  
	 protected DB createInstance() throws Exception {                                                    
	     //这里的username和password可以为null，morphia对象会去处理                                              
		DB ds = mongoClient.getDB( dbName);   
	     
	                                                                                                       
	     return ds; 
		//return null;
	 }                                                                                                          
	                                                                                                            
	 @Override                                                                                                  
	 public Class<?> getObjectType() {                                                                          
	     return Datastore.class;                                                                                
	 }                                                                                                          
	                                                                                                            
	 @Override                                                                                                  
	 public void afterPropertiesSet() throws Exception {                                                        
	     super.afterPropertiesSet();                                                                            
	     if (mongoClient == null) {                                                                                   
	         throw new IllegalStateException("mongo is not set");                                               
	     }                                                                                                                                                                                                        
	 }                                                                                                          
	                                                                                                            
	 /*----------------------setters-----------------------*/                                                   

}
