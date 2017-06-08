package com.btxy.basis.morphia.model.test;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="LoopUpInfo", noClassnameStored=true) 
public class LookUpCode {
	@Id
	private ObjectId id;
	private String LoopUpInfoId;
	private String LookUpTypeId;
	private String LoopUpInfoName;
	
	public String getLookUpTypeId() {
		return LookUpTypeId;
	}
	public void setLookUpTypeId(String lookUpTypeId) {
		LookUpTypeId = lookUpTypeId;
	}
	public String getLoopUpInfoId() {
		return LoopUpInfoId;
	}
	public void setLoopUpInfoId(String loopUpInfoId) {
		LoopUpInfoId = loopUpInfoId;
	}
	public String getLoopUpInfoName() {
		return LoopUpInfoName;
	}
	public void setLoopUpInfoName(String loopUpInfoName) {
		LoopUpInfoName = loopUpInfoName;
	}
	
	
	
}
