package com.btxy.basis.cache.ehcache;


import com.btxy.basis.common.SpringContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

public class ObjectCache {
	private static ObjectCache objectCache;
	
	public static ObjectCache getInstance(){
		if(objectCache==null){
			objectCache=new ObjectCache();
		}
		return objectCache;
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
	public ObjectCache(){
		cache =(Cache ) SpringContext.getApplicationContext().getBean("objectCache");
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
		// TODO Auto-generated method stub
		if( cache.get(string)==null){
			return false;
		}else{
			return true;
		}
	}

	public Object get(String string) {
		// TODO Auto-generated method stub
		if( cache.get(string)==null || cache.get(string).getObjectValue()==null){
			return null;
		}else{
			return cache.get(string).getObjectValue();
		}
	}

	public void set(String string, Object string2) {
		// TODO Auto-generated method stub
		Element element = new Element(string, string2);
		cache.put(element);
	
	}
	
	public void del(String string) {
		// TODO Auto-generated method stub
		if( cache.get(string)!=null){
			cache.remove(string);
		}
	}
}
