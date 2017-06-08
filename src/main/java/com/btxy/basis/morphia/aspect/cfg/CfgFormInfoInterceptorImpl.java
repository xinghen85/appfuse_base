

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.cache.cfg.CfgFormInfoCache;
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgFormInfoInterceptorImpl implements ModelInterceptorInterface<CfgFormInfo> {

    @Override
	public void onChange(CfgFormInfo t, int type) {
		System.out.println("======come here:CfgFormInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgFixedPropertyDefineCache.refreshAll();
		CfgFormInfoCache.refresh(CfgFormInfoCache.class);
	}  
}
