package com.btxy.basis.common.recycle;

import org.mongodb.morphia.annotations.Transient;
import org.springframework.stereotype.Component;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.morphia.model.test.Address;
public class Fk<T> {
	public Fk(){
		//object=new T();
	}
	public Fk(Long pi){
		this.value=pi;
	}
	
	Long value;
	@Transient
	T object;
	@Transient
	boolean ifLoad=false;
	
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public T getObject() {
		
		if(!ifLoad){
			//Class<T> c = T.class;
			System.out.println("====延迟加载====");
			//object= (T)SpringContext.getDatastore().get(T.class, value);
			ifLoad=true;
		}
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	
}
