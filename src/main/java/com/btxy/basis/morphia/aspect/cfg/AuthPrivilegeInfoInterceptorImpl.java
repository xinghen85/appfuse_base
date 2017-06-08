

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.AuthAppRoleCache;
import com.btxy.basis.cache.cfg.AuthPrivilegeInfoCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.model.AuthPrivilegeInfo;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class AuthPrivilegeInfoInterceptorImpl implements ModelInterceptorInterface<AuthPrivilegeInfo> {

    @Override
	public void onChange(AuthPrivilegeInfo t, int type) {
		System.out.println("======come here:AuthPrivilegeInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
		AuthPrivilegeInfoCache.refresh(AuthPrivilegeInfoCache.class);
		
		Cache.refreshAppAll();
		AuthAppRoleCache.refreshAll();
	}  
}
