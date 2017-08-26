package com.btxy.basis.morphia.aspect.base;

import java.io.Serializable;


public interface ModelInterceptorInterface<T extends Serializable> {
	
	public void onChange(T t,int type);
		
}
