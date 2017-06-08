

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.AuthAppRoleCache;
import com.btxy.basis.cache.cfg.AuthShortCutPrivilegeCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.model.AuthShortCutPrivilege;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class AuthShortCutPrivilegeInterceptorImpl implements ModelInterceptorInterface<AuthShortCutPrivilege> {

    @Override
	public void onChange(AuthShortCutPrivilege t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:AuthShortCutPrivilegeInterceptorImpl=====[t:"+t+",type:"+type+"]");
		AuthShortCutPrivilegeCache.refresh(AuthShortCutPrivilegeCache.class);

		Cache.refreshAppAll();
		AuthAppRoleCache.refreshAll();
	}  
}
