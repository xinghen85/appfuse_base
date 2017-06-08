

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.CfgFixedPropertyDefineCache;
import com.btxy.basis.model.CfgFixedPropertyValue;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgFixedPropertyValueInterceptorImpl implements ModelInterceptorInterface<CfgFixedPropertyValue> {

    @Override
	public void onChange(CfgFixedPropertyValue t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come CfgFixedPropertyValueInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgFixedPropertyDefineCache.refresh(CfgFixedPropertyDefineCache.class,t.getLibrary());
	}  
}
