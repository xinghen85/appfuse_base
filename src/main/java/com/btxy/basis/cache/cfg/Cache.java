package com.btxy.basis.cache.cfg;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.AuthOrgUser;
import com.btxy.basis.service.st.AuthAppUserManager;
import com.btxy.basis.service.st.AuthOrgUserManager;

public class Cache {

	private static volatile Map<String,AuthUserCache<AuthOrgUser>> instanceMap=new Hashtable<String,AuthUserCache<AuthOrgUser>>();
	private static volatile Map<String,AuthUserCache<AuthAppUser>> instanceAppMap=new Hashtable<String,AuthUserCache<AuthAppUser>>();
	private static ApplicationContext ctx;
	public  static void startupInit(ApplicationContext ctx1){
		ctx=ctx1;
	}

	public static AuthUserCache<AuthOrgUser> getInstance(Long userId,Long library){
		synchronized(AuthUserCache.class){
			String key=library+"-"+userId;
			
			if(!instanceMap.containsKey(key)){
				AuthOrgUserManager manager=(AuthOrgUserManager) ctx.getBean("authOrgUserManager");
				instanceMap.put(key, new AuthUserCache<AuthOrgUser>(manager,userId,library));
			}
			return instanceMap.get(key);
		}
	}
	public static AuthUserCache<AuthAppUser> getAppInstance(Long userId,Long library){
		synchronized(AuthUserCache.class){
			String key=library+"-"+userId;
			
			if(!instanceAppMap.containsKey(key)){
				AuthAppUserManager manager=(AuthAppUserManager) ctx.getBean("authAppUserManager");
				instanceAppMap.put(key, new AuthUserCache<AuthAppUser>(manager,userId,library));
			}
			return instanceAppMap.get(key);
		}
	}
	public static void refresh(Long userId,Long library){
		synchronized(AuthUserCache.class){
			String key=library+"-"+userId;
			instanceMap.remove(key);
		}
	}
	public static void refreshAll(){
		synchronized(AuthUserCache.class){
			instanceMap.clear();
		}
	}
	public static void refreshApp(Long userId,Long library){
		synchronized(AuthUserCache.class){
			String key=library+"-"+userId;
			instanceAppMap.remove(key);
		}
	}
	public static void refreshAppAll(){
		synchronized(AuthUserCache.class){
			instanceAppMap.clear();
		}
	}
}
