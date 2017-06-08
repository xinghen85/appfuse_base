package com.btxy.basis.webapp.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.btxy.basis.cache.ehcache.ObjectUpdateMsgCache;
import com.btxy.basis.common.ScheduleBean;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.util.BatchUpdateCtl;
import com.btxy.basis.model.LogModelChangeInfo;
import com.mongodb.BasicDBObject;


public class UpdateLazyOpeScheduleBean extends ScheduleBean {
	
	private static final Log log = LogFactory.getLog(UpdateLazyOpeScheduleBean.class);
	
	public UpdateLazyOpeScheduleBean(ServletContext context1) {
		super();
		// TODO Auto-generated constructor stub
	}
	SimpleDateFormat sdf=new SimpleDateFormat("yyMM");
	@Override
	public void dojob() {
		// TODO Auto-generated method stub
		log.info("==========UpdateLazyOpeScheduleBean come here["+new Date()+"]===========");
		Cache cache=ObjectUpdateMsgCache.getInstance().getCache();
		
		String logId=sdf.format(new Date());
		if(cache.getKeys()!=null){

			for(Object key:cache.getKeys()){
				String keystr=(String)key;
				if(keystr!=null && keystr.startsWith("obj-")){
					String[] a=keystr.split("-");
					BasicDBObject doc = new BasicDBObject();  
					doc.put("tm", new Date());
					doc.put("sys", "admin");
					doc.put("usr", 0l);
					doc.put("tp", a[1]);
					doc.put("obj", a[2]);
					doc.put("id", a[3]);
					
					doc.put("detail", cache.get(key).getObjectValue());
					
					BatchUpdateCtl.getInstance(SpringContext.getDbForLog(), "dt_object_instance_log_"+logId).appendItem(doc);
				}else if(keystr!=null && keystr.startsWith("md_")){
					
					LogModelChangeInfo log1=(LogModelChangeInfo)cache.get(key).getObjectValue();
					BasicDBObject doc = new BasicDBObject();  
					doc.put("tm", new Date());
					doc.put("sys", "admin");
					doc.put("usr", 0l);
					doc.put("form", log1.getObject().getClass().getName());
					
					doc.put("detail", log1.getObject()!=null?log1.getObject().toString():"");
					
					BatchUpdateCtl.getInstance(SpringContext.getDbForLog(), "dt_model_log_"+logId).appendItem(doc);
				}
				cache.remove(key);
			}
			BatchUpdateCtl.getInstance(SpringContext.getDbForLog(), "dt_object_instance_log_"+logId).flush();
			BatchUpdateCtl.getInstance(SpringContext.getDbForLog(), "dt_model_log_"+logId).flush();
		}
	}

	
}
