

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.cache.cfg.CfgStateMachineDefineCache;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgStateMachineDefineInterceptorImpl implements ModelInterceptorInterface<CfgStateMachineDefine> {

    @Override
	public void onChange(CfgStateMachineDefine t, int type) {
		System.out.println("======come here:CfgStateMachineDefineInterceptorImpl=====[t:"+t+",type:"+type+"]");
		CfgStateMachineDefineCache.refresh(CfgStateMachineDefineCache.class);
	}  
}
