

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.cache.cfg.AuthAppRoleCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.model.AuthAppRole;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class AuthAppRoleInterceptorImpl implements ModelInterceptorInterface<AuthAppRole> {

    @Override
	public void onChange(AuthAppRole t, int type) {
		System.out.println("======come here:AuthAppRoleInterceptorImpl=====[t:"+t+",type:"+type+"]");
		AuthAppRoleCache.refreshAll();
		Cache.refreshAppAll();
	}  
}
