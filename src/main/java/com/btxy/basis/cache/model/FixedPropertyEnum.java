package com.btxy.basis.cache.model;

import java.io.Serializable;

public class FixedPropertyEnum implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long id;
	String fullId;
	
	public String getFullId() {
		return fullId;
	}
	public void setFullId(String fullId) {
		this.fullId = fullId;
	}
	String name;
	String fullName;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
