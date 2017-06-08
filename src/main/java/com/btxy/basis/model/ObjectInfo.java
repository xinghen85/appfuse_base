package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String cname;
	
	
	String table;
	String pkey;
	boolean ifSchoolCache;
	
	String type;
	String ab;

	
	public List<ObjectAttributeDefine> getAttributes() {
		return attributes;
	}


	public void setAttributes(List<ObjectAttributeDefine> attributes) {
		this.attributes = attributes;
	}


	List<ObjectAttributeDefine> attributes=new ArrayList<ObjectAttributeDefine>();


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCname() {
		return cname;
	}


	public void setCname(String cname) {
		this.cname = cname;
	}


	public String getTable() {
		return table;
	}


	public void setTable(String table) {
		this.table = table;
	}


	public String getPkey() {
		return pkey;
	}


	public void setPkey(String pkey) {
		this.pkey = pkey;
	}


	public boolean isIfSchoolCache() {
		return ifSchoolCache;
	}


	public void setIfSchoolCache(boolean ifSchoolCache) {
		this.ifSchoolCache = ifSchoolCache;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getAb() {
		return ab;
	}


	public void setAb(String ab) {
		this.ab = ab;
	}


	

}
