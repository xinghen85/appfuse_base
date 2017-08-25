

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.CfgStateMachineValueCache;
import com.btxy.basis.model.CfgStateMachineValue;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgStateMachineValueInterceptorImpl implements ModelInterceptorInterface<CfgStateMachineValue> {

    @Override
	public void onChange(CfgStateMachineValue t, int type) {
		System.out.println("======come here:CfgStateMachineValueInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgStateMachineValueCache.refresh(CfgStateMachineValueCache.class);
	}  
}
