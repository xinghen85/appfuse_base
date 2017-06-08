package com.btxy.basis.common.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class OrderedMap<PK,T>  implements Map<PK,T>{
	List<PK> keyList=new ArrayList<PK>();
	List<T> list=new ArrayList<T>();
	Map<PK,T> map=new HashMap<PK,T>();
	
	public int size() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.size()==0;
	}
	
	
	public Iterator<T> iterator() {
		return list.iterator();
	}
	
	public Object[] toArray() {
		return list.toArray();
	}
	
	public List<T> getList() {
		return list;
	}
	public List<PK> getKeyList() {
		return keyList;
	}
	public Map<PK, T> getMap() {
		return map;
	}

	public void clear() {
		map.clear();
		list.clear();
		keyList.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public T get(Object key) {
		return map.get(key);
	}

	@Override
	public T put(PK key, T value) {
		if(!map.containsKey(key)){
			keyList.add(key);
			list.add(value);
			map.put(key, value);
			return value;
		}
		return value;
	}

	@Override
	public T remove(Object key) {
		if(map.containsKey(key)){
			keyList.remove(key);
			list.remove(map.get(key));
		}
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends PK, ? extends T> m) {
		if(m!=null){
			for(PK key:m.keySet()){
				this.put(key, m.get(key));
			}
		}
	}

	@Override
	public Set<PK> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<T> values() {
		return list;
	}

	@Override
	public Set<java.util.Map.Entry<PK, T>> entrySet() {
		return map.entrySet();
	}
}
