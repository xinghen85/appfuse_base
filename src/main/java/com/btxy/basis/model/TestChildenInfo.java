package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;




@Entity


public class TestChildenInfo implements Serializable{
	@Id
	private Long index;
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	private String code;
	private String value;
	
	private boolean constantName;

	@Column(name="code", nullable = false)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="value", nullable = false)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	@Column(name="constantName", nullable = false)
	public boolean isConstantName() {
		return constantName;
	}

	public void setConstantName(boolean constantName) {
		this.constantName = constantName;
	}	
	
	
}
