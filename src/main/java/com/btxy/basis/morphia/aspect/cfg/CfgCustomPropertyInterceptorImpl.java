

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgCustomPropertyInterceptorImpl implements ModelInterceptorInterface<CfgCustomProperty> {

    @Override
	public void onChange(CfgCustomProperty t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:CfgCustomPropertyInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgFixedPropertyDefineCache.refresh(CfgFixedPropertyDefineCache.class,t.getLibrary());
	}  
}
