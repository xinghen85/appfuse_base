package com.btxy.basis.data.clean;

import com.btxy.basis.common.SpringContext;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class CleanForJinJiang {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		{
			DBCollection c1=SpringContext.getDatastore().getDB().getCollection("dt_object_8");
			c1.remove(new BasicDBObject("p172", new BasicDBObject("$ne", 248)));
			
		}
	}

}
/*{
DBCollection c1=SpringContext.getDatastore().getDB().getCollection("dt_object_7");
DBCursor cursor=c1.find();
while(cursor.hasNext()){
	DBObject dbobject=cursor.next();
	if(dbobject.containsField("p174") && dbobject.get("p174").toString().equals("248")){
		System.out.println("1:"+dbobject);
	}else{
		System.out.println("2:"+dbobject);
	}
}
//c1.remove(new BasicDBObject("p174", null));

}*/