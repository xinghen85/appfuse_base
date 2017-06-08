package com.btxy.basis.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.appfuse.anno.AnnoInfo;
import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import com.btxy.basis.common.SpringContext;

@Entity(value="cfg_form_info", noClassnameStored=true) 
@TableAnnoExtend(description="对象",textSearch=true,combinedSearch=false,parent="CfgFormInfo")
@javax.persistence.Entity
@javax.persistence.Table(name="CfgFormInfo",catalog="bsquiz")
public class CfgFormInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="对象ID")
	private Long formId;
	
	@FieldAnnoExtend(description="对象名称",required=true,name=true)
	private String formName;
	
	@FieldAnnoExtend(description="对象标识",required=true)
	private String formCode;
	
	//private String mongodbDocumentName;
	@FieldAnnoExtend(description="对象路径")
	private String modelClassName;
	
	@FieldAnnoExtend(type=7)
	private Long parent;
	
	@FieldAnnoExtend(type=1,enumCode="BB",description="值变化拦截处理类型",required=false)
	private String valueChangeDoType;//List,MultiList,TreeList
	
	/*private List<CfgCustomProperty> customPropertyList=new ArrayList<CfgCustomProperty>();
	
	private List<CfgFixedProperty> fixedPropertyList=new ArrayList<CfgFixedProperty>();
	
	
	public List<CfgFixedProperty> getFixedPropertyList() {
		return fixedPropertyList;
	}
	public void setFixedPropertyList(List<CfgFixedProperty> fixedPropertyList) {
		this.fixedPropertyList = fixedPropertyList;
	}
	public List<CfgCustomProperty> getCustomPropertyList() {
		return customPropertyList;
	}
	public void setCustomPropertyList(List<CfgCustomProperty> customPropertyList) {
		this.customPropertyList = customPropertyList;
	}*/
	
	
	public Long getParent() {
		return parent;
	}
	public String getValueChangeDoType() {
		return valueChangeDoType;
	}
	public void setValueChangeDoType(String valueChangeDoType) {
		this.valueChangeDoType = valueChangeDoType;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	public String getModelClassName() {
		return modelClassName;
	}
	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}
	/*public String getMongodbDocumentName() {
		return mongodbDocumentName;
	}
	public void setMongodbDocumentName(String mongodbDocumentName) {
		this.mongodbDocumentName = mongodbDocumentName;
	}*/
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormCode() {
		return formCode;
	}
	public void setFormCode(String formCode) {
		this.formCode = formCode;
	}
	
	
	public static void main(String[] args) {
		Datastore ds=SpringContext.getDatastore();
		List<CfgFormInfo> list=ds.find(CfgFormInfo.class).asList();
		for(CfgFormInfo one:list){
			one.setParent(0l);
			ds.save(one);
		}
		
	}
}
