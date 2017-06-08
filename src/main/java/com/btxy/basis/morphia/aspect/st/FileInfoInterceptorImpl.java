

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.model.FileInfo;
import org.springframework.stereotype.Component;


import com.btxy.basis.morphia.aspect.base.ModelInterceptorImpl;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class FileInfoInterceptorImpl implements ModelInterceptorInterface<FileInfo> {

    @Override
	public void onChange(FileInfo t, int type) {
		// TODO Auto-generated method stub
		System.out.println("======come here:FileInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
