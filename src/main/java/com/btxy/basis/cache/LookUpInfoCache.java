package com.btxy.basis.cache;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.mongodb.morphia.query.Query;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.btxy.basis.cache.cfg.CfgFormInfoCache;
import com.btxy.basis.cache.model.ExtendFormInfo;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.LabelValue;
//lyz import com.letv.flow.manage.service.GenericManager;


public class LookUpInfoCache {
	private static Map<String,LookUpInfoCache> lookUpCacheMap=new HashMap<String,LookUpInfoCache>();;
	public final static String DATA_SOURCE_MONGODB = "mongodb";
	public final static String DATA_SOURCE_HIBERNATE = "hibernate";
	
	public static LookUpInfoCache getInstance(String name){
		if(!lookUpCacheMap.containsKey(name)){
			LookUpInfoCache cache1=new LookUpInfoCache(name);
			lookUpCacheMap.put(name, cache1);
		}
		return lookUpCacheMap.get(name);
	}
	
	Class<?> formClass;
	
	String formName;
	ExtendFormInfo formInfo;
	Map<Long,String> idNameMap;
	Field nameField=null;
	Field idField=null;
	
	Method nameGetMethod;
	Method idGetMethod;
	public void init(){
		formInfo=CfgFormInfoCache.getInstance().getCfgFormInfoByFormCode(formName);
		formClass=formInfo.getFormClass();
		idField=formInfo.getIdField();
		nameField=formInfo.getNameField();
		idGetMethod=formInfo.getIdGetMethod();
		nameGetMethod=formInfo.getNameGetMethod();
		idNameMap=new HashMap<Long,String>();
	}
	public String getNameById(Long id,String dataSource){
		if(nameField==null){
			return id==null?"":id.toString();
		}else{
			Object obj=null;
			if (DATA_SOURCE_HIBERNATE.equals(dataSource)) {
			//lyz	GenericManager manager = SpringContext.getApplicationContext().getBean(formName+"Manager", GenericManager.class);
			//lyz	obj=manager.get(id);
			}else {
				obj=SpringContext.getDatastore().get(formClass,id);
			}
			
			
			
			if(obj!=null){
				String name=null;
				if (nameField.getType().getName().equals(  
	                    java.lang.String.class.getName())) {  
	                // String type  
	                try {  
	                	
	                	name=(String) nameGetMethod.invoke(obj);//执行get方法返回一个Object
	                } catch (IllegalArgumentException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (IllegalAccessException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	            }
				return name;
			}else{
				return null;
			}
	
		}
	
	}
	
	public List<LabelValue> getIdNameList(String filter,String dataSource){
		List<LabelValue> list=new ArrayList<LabelValue>();
		List<?> objlist = null;
		try {
			if (DATA_SOURCE_HIBERNATE.equals(dataSource)) {
				//lyz  GenericManager manager = SpringContext.getApplicationContext().getBean(formName+"Manager", GenericManager.class);
				DetachedCriteria dc = DetachedCriteria.forClass(formClass);
				
				if (StringUtils.isNotBlank(filter)) {
					JSONObject json = JSON.parseObject(filter);
					Set<String> keys = json.keySet();
					for (String key : keys) {
						dc.add(Restrictions.eq(key, json.get(key)));
					}
				}
				//objlist=manager.findByDetachedCriteria(dc);
			}else {
				Query<?> query = SpringContext.getDatastore().createQuery(formClass);
				if (StringUtils.isNotBlank(filter)) {
					JSONObject json = JSON.parseObject(filter);
					Set<String> keys = json.keySet();
					for (String key : keys) {
						query.field(key).equal(json.get(key));
					}
				}
				objlist=query.asList();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (objlist == null) {
			objlist = SpringContext.getDatastore().find(formClass).asList();
		}
		if(objlist!=null && objlist.size()>0){
			if(nameField!=null && nameField.getType().getName().equals(  
                    java.lang.String.class.getName())){
				 try {  
	                	for(Object objone:objlist){
	                		LabelValue label=new LabelValue();
	                		
		                	String name=(String) nameGetMethod.invoke(objone);//执行get方法返回一个Object
		                	
		                	Long id=(Long) idGetMethod.invoke(objone);//执行get方法返回一个Object
	                		label.setLabel(name);
	                		label.setValue(id==null?"":id.toString());
	                		
	                		list.add(label);
	                	}
	                	
	                } catch (IllegalArgumentException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (IllegalAccessException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
			}else{
				 try {  
	                	for(Object objone:objlist){
	                		LabelValue label=new LabelValue();
	                		
		                	
		                	Long id=(Long) idGetMethod.invoke(objone);//执行get方法返回一个Object
	                		label.setLabel(id==null?"":id.toString());
	                		label.setValue(id==null?"":id.toString());
	                		
	                		list.add(label);
	                	}
	                	
	                } catch (IllegalArgumentException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (IllegalAccessException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			}
			
			
		}
		
		return list;
	
	}
	public List<LabelValue> getNotContainsIdNameList(String filter,String dataSource){
		List<LabelValue> list=new ArrayList<LabelValue>();
		List<?> objlist = null;
		try {
			if (DATA_SOURCE_HIBERNATE.equals(dataSource)) {
				//lyz GenericManager manager = SpringContext.getApplicationContext().getBean(formName+"Manager", GenericManager.class);
				DetachedCriteria dc = DetachedCriteria.forClass(formClass);
				
				if (StringUtils.isNotBlank(filter)) {
					JSONObject json = JSON.parseObject(filter);
					Set<String> keys = json.keySet();
					for (String key : keys) {
						dc.add(Restrictions.eq(key, json.get(key)));
					}
				}
				//objlist=manager.findByDetachedCriteria(dc);
			}else {
				Query<?> query = SpringContext.getDatastore().createQuery(formClass);
				if (StringUtils.isNotBlank(filter)) {
					JSONObject json = JSON.parseObject(filter);
					Set<String> keys = json.keySet();
					for (String key : keys) {
						query.field(key).equal(json.get(key));
					}
				}
				objlist=query.asList();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (objlist == null) {
			objlist = SpringContext.getDatastore().find(formClass).asList();
		}
		if(objlist!=null && objlist.size()>0){
			if(nameField!=null && nameField.getType().getName().equals(  
                    java.lang.String.class.getName())){
				 try {  
	                	for(Object objone:objlist){
	                		LabelValue label=new LabelValue();
	                		
		                	String name=(String) nameGetMethod.invoke(objone);//执行get方法返回一个Object
		                	
		                	Long id=(Long) idGetMethod.invoke(objone);//执行get方法返回一个Object
	                		label.setLabel(name);
	                		label.setValue(id==null?"":id.toString());
	                		
	                		list.add(label);
	                	}
	                	
	                } catch (IllegalArgumentException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (IllegalAccessException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
				
			}else{
				 try {  
	                	for(Object objone:objlist){
	                		LabelValue label=new LabelValue();
	                		
		                	
		                	Long id=(Long) idGetMethod.invoke(objone);//执行get方法返回一个Object
	                		label.setLabel(id==null?"":id.toString());
	                		label.setValue(id==null?"":id.toString());
	                		
	                		list.add(label);
	                	}
	                	
	                } catch (IllegalArgumentException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (IllegalAccessException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                } catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
			}
			
			
		}
		
		return list;
	
	}
	public LookUpInfoCache(String name){
		formName=name;
		init();
	}
	
}
