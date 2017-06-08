package com.btxy.basis.cache.cfg;

import com.btxy.basis.cache.model.BaseCacheByLibrary;
import com.btxy.basis.model.AuthAppRole;

public class AuthAppRoleCache extends BaseCacheByLibrary<AuthAppRole,Long>{
	
	public static AuthAppRoleCache getInstance(Long library){
		return (AuthAppRoleCache) getInstance(AuthAppRoleCache.class,library);	
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public AuthAppRoleCache(Long library) {
		super(AuthAppRole.class,library,true);
	}
	
}
