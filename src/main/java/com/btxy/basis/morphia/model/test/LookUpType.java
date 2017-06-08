package com.btxy.basis.morphia.model.test;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="LookUpType", noClassnameStored=true) 
public class LookUpType {
	@Id
	private ObjectId id;
	private String LookUpTypeId;
	private String LookUpTypeName;
	
	public String getLookUpTypeId() {
		return LookUpTypeId;
	}
	public void setLookUpTypeId(String lookUpTypeId) {
		LookUpTypeId = lookUpTypeId;
	}
	public String getLookUpTypeName() {
		return LookUpTypeName;
	}
	public void setLookUpTypeName(String lookUpTypeName) {
		LookUpTypeName = lookUpTypeName;
	}
	
	
	
}
