package com.btxy.basis.model;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.util.SequenceUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;




@Entity(value="cfg_sequence_info", noClassnameStored=true) 

public class CfgSequenceInfo {
	@Id
	String id;
	Long next;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getNext() {
		return next;
	}
	public void setNext(Long next) {
		this.next = next;
	}
	public static void main(String[] args) {
		String objhead="com.btxy.basis.model.DtObjectInstanceInfo.";
		Datastore ds=SpringContext.getDatastore();
		
		List<CfgSequenceInfo> list=ds.find(CfgSequenceInfo.class).asList();
		for(CfgSequenceInfo one:list){
			String id=one.getId();
			if(id.startsWith(objhead)){
				String foot=id.substring(objhead.length());
				System.out.println("foot:"+foot);
				
				DBCollection coll=ds.getDB().getCollection("dt_object_"+foot);
				BasicDBObject keys = new BasicDBObject();  
		        keys.put("_id", -1);  
		        DBCursor cursor = coll.find().sort(keys).limit(1);  
		        while(cursor.hasNext()){  
		            //System.out.println(cursor.next().toString());
		            
		            one.setNext((Long)cursor.next().get("_id")+1);
		            ds.save(one);
		        }  
			}else{
				try {
					Class<?> cc=Class.forName(id);
					Query<?>  q=ds.createQuery(cc);
					q.order("_id");
					List<?> ll=q.limit(1).asKeyList();
					if(ll!=null && ll.size()>0){
						Key<?> onekey=(Key<?>)ll.get(0);
						System.out.println(onekey.getId());
					}
					
					//SequenceUtil.getNext(cc.);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
