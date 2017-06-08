

package com.btxy.basis.morphia.aspect.cfg;

import com.btxy.basis.model.CfgIconInfo;
import org.springframework.stereotype.Component;


import com.btxy.basis.morphia.aspect.base.ModelInterceptorImpl;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class CfgIconInfoInterceptorImpl implements ModelInterceptorInterface<CfgIconInfo> {

    @Override
	public void onChange(CfgIconInfo t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:CfgIconInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
