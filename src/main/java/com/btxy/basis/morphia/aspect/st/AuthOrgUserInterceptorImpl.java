

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.model.AuthOrgUser;
import org.springframework.stereotype.Component;


import com.btxy.basis.morphia.aspect.base.ModelInterceptorImpl;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class AuthOrgUserInterceptorImpl implements ModelInterceptorInterface<AuthOrgUser> {

    @Override
	public void onChange(AuthOrgUser t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:AuthOrgUserInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
