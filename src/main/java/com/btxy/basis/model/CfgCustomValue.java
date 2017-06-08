package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.util.SequenceUtil;

@Entity(noClassnameStored=true) 

public class CfgCustomValue implements Serializable{
	
	@Id
	private Long customValueId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;
	private List<CfgCustomValue> childen=new ArrayList<CfgCustomValue>();
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<CfgCustomValue> getChilden() {
		return childen;
	}
	public void setChilden(List<CfgCustomValue> childen) {
		this.childen = childen;
	}
	public CfgCustomValue(){
		
	}
	public CfgCustomValue(String value1){
		this.value=value1;
	}
}
