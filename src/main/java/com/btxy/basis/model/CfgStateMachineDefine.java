package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;

import com.mongodb.DBObject;

@Entity(value="cfg_state_machine_define", noClassnameStored=true) 
@TableAnnoExtend(description="状态机",textSearch=false,combinedSearch=true,parent="" ,foreignParent="")
@javax.persistence.Entity
@javax.persistence.Table(name="CfgStateMachine",catalog="bsquiz")
public class CfgStateMachineDefine implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="状态机ID")
	private Long machineId;
	
	@FieldAnnoExtend(description="状态机名称",required=true,textSearch=true,name=true)
	String machineName;

	@FieldAnnoExtend(type=9,foreignModel="CfgEnumInfo",description="枚举变量",required=true,combinedSearch=true)
	Long enumId;
	
	@FieldAnnoExtend(type=9,description="对象",foreignModel="CfgFormInfo")
	Long formId;
	
	@FieldAnnoExtend(description="对象属性定义",required=true)
	String fieldName;
	
	@FieldAnnoExtend(description="刷新状态URL",required=true)
	String refreshStateUrl;

	
	@Transient
	@javax.persistence.Transient
	CfgEnumInfo enumInfo;
	
	

	public CfgEnumInfo getEnumInfo() {
		return enumInfo;
	}

	public void setEnumInfo(CfgEnumInfo enumInfo) {
		this.enumInfo = enumInfo;
	}

	public String getRefreshStateUrl() {
		return refreshStateUrl;
	}

	public void setRefreshStateUrl(String refreshStateUrl) {
		this.refreshStateUrl = refreshStateUrl;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Long getMachineId() {
		return machineId;
	}

	public void setMachineId(Long machineId) {
		this.machineId = machineId;
	}

	

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public Long getEnumId() {
		return enumId;
	}

	public void setEnumId(Long enumId) {
		this.enumId = enumId;
	}
	
	
	
}
