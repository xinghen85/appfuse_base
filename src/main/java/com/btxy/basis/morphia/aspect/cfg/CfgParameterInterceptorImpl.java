

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.common.ConfigureContext;
import com.btxy.basis.model.CfgParameter;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgParameterInterceptorImpl implements ModelInterceptorInterface<CfgParameter> {

    @Override
	public void onChange(CfgParameter t, int type) {
		System.out.println("======come here:CfgParameterInterceptorImpl=====[t:"+t+",type:"+type+"]");
		ConfigureContext.refreshDbParameters();
	}  
}
