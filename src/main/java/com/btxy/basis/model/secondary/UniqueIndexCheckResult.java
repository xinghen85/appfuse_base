package com.btxy.basis.model.secondary;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

public class UniqueIndexCheckResult {
	private List<String> errors=new ArrayList<String>();
	private boolean ifOk;
	private Long existId;
	
	private DBObject dbObject;
	
	
	public DBObject getDbObject() {
		return dbObject;
	}
	public void setDbObject(DBObject dbObject) {
		this.dbObject = dbObject;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public boolean isIfOk() {
		return ifOk;
	}
	public void setIfOk(boolean ifOk) {
		this.ifOk = ifOk;
	}
	public Long getExistId() {
		return existId;
	}
	public void setExistId(Long existId) {
		this.existId = existId;
	}
	
	
	
}
