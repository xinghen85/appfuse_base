package com.btxy.basis.morphia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataStatItemInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String cname;
	List<String> dataColumn=new ArrayList<String>();
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
	public List<String> getDataColumn() {
		return dataColumn;
	}
	public void setDataColumn(List<String> dataColumn) {
		this.dataColumn = dataColumn;
	}
}
