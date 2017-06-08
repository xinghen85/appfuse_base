

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.model.StSystemNoticeInfo;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class StSystemNoticeInfoInterceptorImpl implements ModelInterceptorInterface<StSystemNoticeInfo> {

    @Override
	public void onChange(StSystemNoticeInfo t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:StSystemNoticeInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
