package com.btxy.basis.common.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.SequenceInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class SequenceUtil {
	protected static final boolean debug=true;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	protected static Map<String,List<Long>> map=new Hashtable<String,List<Long>>();
	public static synchronized Long getNext(Class<?> c){
		if(debug){
			
				DBObject ret = SpringContext.getDatastore().getCollection(SequenceInfo.class).findAndModify(new BasicDBObject("_id",c.getName()),null,null,false,
						new BasicDBObject("$inc", new BasicDBObject("next",1)),true,true);
				long incId = Long.valueOf(ret.get("next").toString());
				return incId;
		}else{
			if(!map.containsKey(c.getName())){
				map.put(c.getName(), new ArrayList<Long>());
			}
			if(map.get(c.getName()).size()==0){
				DBObject ret = SpringContext.getDatastore().getCollection(SequenceInfo.class).findAndModify(new BasicDBObject("_id",c.getName()),null,null,false,
						new BasicDBObject("$inc", new BasicDBObject("next",100)),true,true);
				long incId = Long.valueOf(ret.get("next").toString());
				//Set set=new HashSet<Long>();
				for(int i=0;i<100;i++){
					//set.add(incId-i);
					map.get(c.getName()).add(incId-i);
				}
				
			}
			long rid=map.get(c.getName()).get(map.get(c.getName()).size()-1);
			map.get(c.getName()).remove(map.get(c.getName()).size()-1);
			return rid;
		}
		
		
	}
	public static synchronized Long getNext(Class<?> c,Long id){
		if(debug){
				DBObject nowId=SpringContext.getDatastore().getCollection(SequenceInfo.class).findOne(new BasicDBObject("_id",c.getName()));
				if(nowId!=null && nowId.get("next")!=null){
					if(id.longValue()>(new Long(nowId.get("next").toString()).longValue())){
						nowId.put("next", id+1);
					}
					SpringContext.getDatastore().getCollection(SequenceInfo.class).save(nowId);					
				}
				return id;
		}else{
			if(!map.containsKey(c.getName())){
				map.put(c.getName(), new ArrayList<Long>());
			}
			if(map.get(c.getName()).size()==0){
				DBObject ret = SpringContext.getDatastore().getCollection(SequenceInfo.class).findAndModify(new BasicDBObject("_id",c.getName()),null,null,false,
						new BasicDBObject("$inc", new BasicDBObject("next",100)),true,true);
				long incId = Long.valueOf(ret.get("next").toString());
				//Set set=new HashSet<Long>();
				for(int i=0;i<100;i++){
					//set.add(incId-i);
					map.get(c.getName()).add(incId-i);
				}
				
			}
			long rid=map.get(c.getName()).get(map.get(c.getName()).size()-1);
			map.get(c.getName()).remove(map.get(c.getName()).size()-1);
			return rid;
		}
		
		
	}
}
