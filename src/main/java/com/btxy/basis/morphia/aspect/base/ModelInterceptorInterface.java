package com.btxy.basis.morphia.aspect.base;


public interface ModelInterceptorInterface<T> {
	
	public void onChange(T t,int type);
		
}
