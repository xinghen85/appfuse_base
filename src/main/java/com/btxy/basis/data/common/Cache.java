package com.btxy.basis.data.common;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	private static Map map=new HashMap();
	
	public boolean containsKey(Object key){
		return map.containsKey(key);
	}
	public Object get(Object key){
		return map.get(key);
	}
	public void put(Object key,Object bean){
		map.put(key,bean);
	}
	public void remove(Object key){
		map.remove(key);
	}
}
