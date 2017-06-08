package com.btxy.basis.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class BatchUpdateCtl {
	static long maxCacheSize=100;
	
	private static Map<String,BatchUpdateCtl> collectionInsertCtlMap=new HashMap<String,BatchUpdateCtl>();
	
	public static BatchUpdateCtl getInstance(DB db,String collectionName){
		
		String key=db.getName()+"_"+collectionName;
		if(!collectionInsertCtlMap.containsKey(key)){
			//collectionInsertCtlMap.clear();
			collectionInsertCtlMap.put(key, new BatchUpdateCtl(db,collectionName));
		}
		return collectionInsertCtlMap.get(key);
	}
	
	DBCollection collection=null;
	DB db;
	String collectionName;
	public BatchUpdateCtl(DB db1, String collectionName1) {
		this.db=db1;
		this.collectionName=collectionName1;
		this.collection=db.getCollection(collectionName);
	}
	List<DBObject> cache=new ArrayList<DBObject>();
	public void appendItem(DBObject basicDBObject){
		cache.add(basicDBObject);
		if(cache.size()>=maxCacheSize){
			//System.out.println("======插入数据库======");
			/*for(DBObject one:cache){
				System.out.println("	"+one.get("_id"));
			}*/
			collection.insert(cache);
			cache=new ArrayList<DBObject>();
		}
		
	} 
	
	public void flush(){
		if(cache!=null && cache.size()>0){
			collection.insert(cache);
			cache=new ArrayList<DBObject>();
		}
	}
	public void drop() {
		//BasicDBObject query=new BasicDBObject();
		//collection.remove(query);
		collection.drop();
		this.collection=db.getCollection(collectionName);
	}
	public void clear() {
		if(cache!=null && cache.size()>0){
			cache.clear();
			cache=new ArrayList<DBObject>();
		}
	}
}
