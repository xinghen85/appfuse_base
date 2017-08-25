

package com.btxy.basis.morphia.aspect.st;

import com.btxy.basis.model.FileInfo;
import com.btxy.basis.morphia.aspect.base.ModelInterceptorInterface;


public class FileInfoInterceptorImpl implements ModelInterceptorInterface<FileInfo> {

    @Override
	public void onChange(FileInfo t, int type) {
		System.out.println("======come here:FileInfoInterceptorImpl=====[t:"+t+",type:"+type+"]");
	}  
}
