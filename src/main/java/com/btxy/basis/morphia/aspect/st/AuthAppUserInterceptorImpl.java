

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.cache.cfg.AuthAppRoleCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class AuthAppUserInterceptorImpl implements ModelInterceptorInterface<AuthAppUser> {

    @Override
	public void onChange(AuthAppUser t, int type) {
		System.out.println("======come here:AuthAppUserInterceptorImpl=====[t:"+t+",type:"+type+"]");
		AuthAppRoleCache.refreshAll();
		Cache.refreshAppAll();
	}  
}
