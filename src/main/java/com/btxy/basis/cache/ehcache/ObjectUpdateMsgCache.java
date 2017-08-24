package com.btxy.basis.cache.ehcache;


import com.btxy.basis.common.SpringContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class ObjectUpdateMsgCache {
	private static ObjectUpdateMsgCache objectUpdateMsgCache;
	
	public static ObjectUpdateMsgCache getInstance(){
		if(objectUpdateMsgCache==null){
			objectUpdateMsgCache=new ObjectUpdateMsgCache();
		}
		return objectUpdateMsgCache;
	}
	
	private Cache  cache;
	
	
	public Cache getCache() {
		return cache;
	}
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	public  void shuttdown(){
		this.cache.getCacheManager().shutdown();
	}
	public ObjectUpdateMsgCache(){
		cache =(Cache ) SpringContext.getApplicationContext().getBean("objectUpdateMsgCache");
	}
	
	boolean ready=false;

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	
	public void flush(){
		cache.flush();
	}

	public boolean exists(String string) {
		if( cache.get(string)==null){
			return false;
		}else{
			return true;
		}
	}

	public Object get(String string) {
		if( cache.get(string)==null || cache.get(string).getObjectValue()==null){
			return null;
		}else{
			return cache.get(string).getObjectValue();
		}
	}

	public void set(String string, Object string2) {
		Element element = new Element(string, string2);
		cache.put(element);
	
	}
	
	public void del(String string) {
		if( cache.get(string)!=null){
			cache.remove(string);
		}
	}
}
