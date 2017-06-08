

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.model.LibraryInfo;
import org.springframework.stereotype.Component;


import com.btxy.basis.morphia.aspect.base.ModelInterceptorImpl;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class LibraryInfoInterceptorImpl implements ModelInterceptorInterface<LibraryInfo> {

    @Override
	public void onChange(LibraryInfo t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:LibraryInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
