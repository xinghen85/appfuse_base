

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.AuthAppRoleCache;
import com.btxy.basis.cache.cfg.Cache;
import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.model.CfgFixedPropertyDefine;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgFixedPropertyDefineInterceptorImpl implements ModelInterceptorInterface<CfgFixedPropertyDefine> {

    @Override
	public void onChange(CfgFixedPropertyDefine t, int type) {
		System.out.println("======come CfgFixedPropertyDefineInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgFixedPropertyDefineCache.refreshAll();

		Cache.refreshAppAll();
		AuthAppRoleCache.refreshAll();
	}  
}
