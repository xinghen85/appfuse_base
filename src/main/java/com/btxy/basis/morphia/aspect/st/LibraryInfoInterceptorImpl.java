

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.model.LibraryInfo;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class LibraryInfoInterceptorImpl implements ModelInterceptorInterface<LibraryInfo> {

    @Override
	public void onChange(LibraryInfo t, int type) {
		System.out.println("======come here:LibraryInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
