package com.btxy.basis.morphia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.ObjectInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


@Entity(value="dm_data_element_info", noClassnameStored=true) 

public class DataElementInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Long getDataElementId() {
		return dataElementId;
	}
	public void setDataElementId(Long dataElementId) {
		this.dataElementId = dataElementId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ObjectInfo> getObjectList() {
		return objectList;
	}
	public void setObjectList(List<ObjectInfo> objectList) {
		this.objectList = objectList;
	}
	public List<EntityInfo> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<EntityInfo> entityList) {
		this.entityList = entityList;
	}
	@Id
	Long dataElementId;
	String name;
	String status;
	List<ObjectInfo> objectList=new ArrayList<ObjectInfo>();
	List<EntityInfo> entityList=new ArrayList<EntityInfo>();

	@Override
	public String toString() {
		return "DataElementInfo [id="  + ", dataElementId=" + dataElementId
				+ ", name=" + name + ", status=" + status + ", objectList="
				+ objectList + ", entityList=" + entityList + "]";
	}
	private long library;
	private boolean overt=false;
	
	public long getLibrary() {
		return library;
	}
	public void setLibrary(long library) {
		this.library = library;
	}
	public boolean isOvert() {
		return overt;
	}
	public void setOvert(boolean overt) {
		this.overt = overt;
	}
}
