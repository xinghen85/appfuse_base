package com.btxy.basis.morphia.model.test;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="sys_code", noClassnameStored=true) 
public class SysCode {
	@Id
	private ObjectId id;
	private String code;
	private String code_prefix;
	private String code_name;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode_prefix() {
		return code_prefix;
	}
	public void setCode_prefix(String code_prefix) {
		this.code_prefix = code_prefix;
	}
	public String getCode_name() {
		return code_name;
	}
	public void setCode_name(String code_name) {
		this.code_name = code_name;
	}
	
	
}
