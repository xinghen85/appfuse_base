package com.btxy.basis.model;

import java.io.Serializable;

public class ObjectAttributeDefine implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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


	public boolean isIfObject() {
		return ifObject;
	}

	public void setIfObject(boolean ifObject) {
		this.ifObject = ifObject;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIfUseQuery() {
		return ifUseQuery;
	}

	public void setIfUseQuery(boolean ifUseQuery) {
		this.ifUseQuery = ifUseQuery;
	}

	String name;
	String cname;
	boolean ifObject;
	String field;
	String type;
	boolean ifUseQuery;
	//ObjectInfo targetObjectDataModel=null;
	//Integer index;
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


}
