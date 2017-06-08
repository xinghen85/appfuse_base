package com.btxy.basis.util.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
	public static <T1,T> void appendListEntityToMap(Map<T1,List<T>> map,T1 key,T obj){
		if(!map.containsKey(key)){
			map.put(key, new ArrayList<T>());
		}
		map.get(key).add(obj);
	}
	
	public static <KEY1,KEY2,T> void appendMapEntityToMap(Map<KEY1,Map<KEY2,T>> map,KEY1 key,KEY2 childKey,T obj){
		if(!map.containsKey(key)){
			map.put(key, new HashMap<KEY2,T>());
		}
		map.get(key).put(childKey,obj);
	}
}
