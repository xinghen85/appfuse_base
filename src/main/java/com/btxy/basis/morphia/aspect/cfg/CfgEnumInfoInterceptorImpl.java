

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.CfgEnumInfoCache;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgEnumInfoInterceptorImpl implements ModelInterceptorInterface<CfgEnumInfo> {

    @Override
	public void onChange(CfgEnumInfo t, int type) {
		System.out.println("======come CfgEnumInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgEnumInfoCache.refresh(CfgEnumInfoCache.class);
	}  
}
