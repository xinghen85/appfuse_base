package com.btxy.basis.common.model;

import java.io.Serializable;
import java.util.List;

public class TreeModelObject<T> implements Serializable{
	public T value;
	public List<T> children;
	public List<T> getChildren() {
		return children;
	}
	public void setChildren(List<T> children) {
		this.children = children;
	}
	
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public TreeModelObject(T t){
		this.value=t;
	}
}
