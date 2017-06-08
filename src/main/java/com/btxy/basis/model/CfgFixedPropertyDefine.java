package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.util.SequenceUtil;
import com.btxy.basis.model.TestChildenInfo;

@Entity(value="cfg_fixed_property_def", noClassnameStored=true) 
@TableAnnoExtend(description="系统可变属性",textSearch=true)
@javax.persistence.Entity
@javax.persistence.Table(name="CfgFixedPropertyDefine",catalog="bsquiz")
public class CfgFixedPropertyDefine implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id	
	@javax.persistence.Id
	@FieldAnnoExtend(description="属性ID")
	private Long propertyId;
	
	
	@FieldAnnoExtend(description="属性名称",required=true)
	private String propertyName;
	@FieldAnnoExtend(description="属性编码",required=true)
	private String propertyCode;
	@FieldAnnoExtend(type=1,enumCode="AX",description="属性值类型",required=true)
	private String valueType;//List,MultiList,TreeList
	
	
	@FieldAnnoExtend(type=6,description="对象",foreignModel="CfgFormInfo")
	@Reference(lazy = true,ignoreMissing=true)  
	private List<CfgFormInfo> formList=new ArrayList<CfgFormInfo>();
	
	@FieldAnnoExtend(type=5,childModel="CfgFixedPropertyField")
	private List<CfgFixedPropertyField> fieldList=new ArrayList<CfgFixedPropertyField>();
	
	
	@FieldAnnoExtend(type=9,description="角色列表",foreignModel="AuthAppRole",multiple=true)
	List<Long> roleList=new ArrayList<Long>();
	
	
	
	public List<Long> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}
	public List<CfgFixedPropertyField> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<CfgFixedPropertyField> fieldList) {
		this.fieldList = fieldList;
	}
	@Transient
	private List<Long> formIdList=new ArrayList<Long>();
		
	
	public List<Long> getFormIdList() {
		return formIdList;
	}
	public void setFormIdList(List<Long> formIdList){
		this.formIdList = formIdList;
	}
	public List<CfgFormInfo> getFormList() {
		return formList;
	}
	public void setFormList(List<CfgFormInfo> formList) {
		this.formList = formList;
	}
	public Long getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyCode() {
		return propertyCode;
	}
	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	
}
