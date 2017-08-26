package com.btxy.basis.cache.model;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.btxy.basis.cache.cfg.AuthPrivilegeInfoCache;
import com.btxy.basis.cache.model.BaseCache;
import com.btxy.basis.model.AuthPrivilegeInfo;

public class TestCache extends BaseCache<AuthPrivilegeInfo,Long>  {

	static ReadWriteLock lock = new ReentrantReadWriteLock(false);  
	
	public static AuthPrivilegeInfoCache getInstance(){
		lock.readLock().lock();
		Class<?> key=AuthPrivilegeInfoCache.class;
		
		if(!instanceMap.containsKey(key)){
			lock.writeLock().lock();
			instanceMap.put(key, new AuthPrivilegeInfoCache());
			lock.writeLock().unlock();
		}
		lock.readLock().unlock();
		return (AuthPrivilegeInfoCache)instanceMap.get(key);
	}
	public static void refresh(){
		lock.writeLock().lock();
		instanceMap.remove(AuthPrivilegeInfoCache.class);
		lock.writeLock().unlock();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	public TestCache(){
		
	}
	
}
