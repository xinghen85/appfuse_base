package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

@Entity(value="auth_privilege_info", noClassnameStored=true) 
@TableAnnoExtend(description="权限",textSearch=true,combinedSearch=true,parent="AuthPrivilegeInfo")
@javax.persistence.Entity
@javax.persistence.Table(name="AuthPrivilegeInfo",catalog="bsquiz")
public class AuthPrivilegeInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="权限ID")
	Long privilegeId;
	@FieldAnnoExtend(description="权限名称",required=true,name=true,textSearch=true)
	String privilegeName;
	@FieldAnnoExtend(type=1,enumCode="AW",pleaseSelect=false,description="权限类型",required=true,combinedSearch=true)
	String privilegeType;
	@FieldAnnoExtend(type=9,description="对象",foreignModel="CfgFormInfo",pleaseSelect=true)
	Long formId=0l;
	@FieldAnnoExtend(type=1,enumCode="AZ",pleaseSelect=true,description="操作名称",combinedSearch=true)
	String operateName;
	@FieldAnnoExtend(description="权限路径")
	String url;
	@FieldAnnoExtend(type=7,description="父权限ID")
	Long parent;
	@FieldAnnoExtend(description="是否按钮组")
	boolean ifButtonGroup;
	//@FieldAnnoExtend(description="任务完成时隐藏")
	//boolean ifHiddenOnTaskNotRunning;
	@FieldAnnoExtend(description="任务未完成时隐藏")
	boolean ifHiddenOnTaskRunning;
	@FieldAnnoExtend(description="图标")
	String iconCode;
	@FieldAnnoExtend(description="排序号")
	double sortNo;
	@FieldAnnoExtend(type=1,enumCode="BF",pleaseSelect=true,description="任务类型",combinedSearch=true)
	String taskType;
	@FieldAnnoExtend(type=9,description="状态机",foreignModel="CfgStateMachineDefine",pleaseSelect=true)
	Long machineId=0l;
	@FieldAnnoExtend(type=1,enumCode="AW",pleaseSelect=false,description="可见状态",multiple=true)
	List<String> enumCodeList=new ArrayList<String>();
	@FieldAnnoExtend(type=1,enumCode="BQ",pleaseSelect=true,description="请求类型",combinedSearch=true)
	String operatorRequestType;
	@FieldAnnoExtend(description="js函数")
	String javaScriptFunction;
	
	@Transient
	@javax.persistence.Transient
	CfgStateMachineDefine stateMachineDefine;
	
	@Transient
	@javax.persistence.Transient
	CfgFormInfo form;
	
	
	
	@Transient
	int row;
	@Transient
	int col;
	@Transient
	boolean ifLeaf;
	
	@Transient
	boolean ifLevelLeaf;
	
	@Transient
	int allChildrenRowSize;
	
	
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public boolean isIfLeaf() {
		return ifLeaf;
	}
	public void setIfLeaf(boolean ifLeaf) {
		this.ifLeaf = ifLeaf;
	}
	public boolean isIfLevelLeaf() {
		return ifLevelLeaf;
	}
	public void setIfLevelLeaf(boolean ifLevelLeaf) {
		this.ifLevelLeaf = ifLevelLeaf;
	}
	public int getAllChildrenRowSize() {
		return allChildrenRowSize;
	}
	public void setAllChildrenRowSize(int allChildrenRowSize) {
		this.allChildrenRowSize = allChildrenRowSize;
	}
	/*public boolean isIfHiddenOnTaskNotRunning() {
		return ifHiddenOnTaskNotRunning;
	}
	public void setIfHiddenOnTaskNotRunning(boolean ifHiddenOnTaskNotRunning) {
		this.ifHiddenOnTaskNotRunning = ifHiddenOnTaskNotRunning;
	}*/
	public boolean isIfHiddenOnTaskRunning() {
		return ifHiddenOnTaskRunning;
	}
	public String getIconCode() {
		return iconCode;
	}
	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
	}
	public void setIfHiddenOnTaskRunning(boolean ifHiddenOnTaskRunning) {
		this.ifHiddenOnTaskRunning = ifHiddenOnTaskRunning;
	}
	public String getOperatorRequestType() {
		return operatorRequestType;
	}
	public void setOperatorRequestType(String operatorRequestType) {
		this.operatorRequestType = operatorRequestType;
	}
	public String getJavaScriptFunction() {
		return javaScriptFunction;
	}
	public void setJavaScriptFunction(String javaScriptFunction) {
		this.javaScriptFunction = javaScriptFunction;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		if(formId!=null){
			this.formId = formId;
		}
	}
	public CfgFormInfo getForm() {
		return form;
	}
	public void setForm(CfgFormInfo form) {
		this.form = form;
	}
	public CfgStateMachineDefine getStateMachineDefine() {
		return stateMachineDefine;
	}
	public void setStateMachineDefine(CfgStateMachineDefine stateMachineDefine) {
		this.stateMachineDefine = stateMachineDefine;
	}
	
	public boolean isIfButtonGroup() {
		return ifButtonGroup;
	}
	public void setIfButtonGroup(boolean ifButtonGroup) {
		this.ifButtonGroup = ifButtonGroup;
	}
	public Long getMachineId() {
		return machineId;
	}
	public void setMachineId(Long machineId) {
		if(machineId!=null){
			this.machineId = machineId;
		}
	}
	public List<String> getEnumCodeList() {
		return enumCodeList;
	}
	public void setEnumCodeList(List<String> enumCodeList) {
		this.enumCodeList = enumCodeList;
	}
	public Long getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getPrivilegeType() {
		return privilegeType;
	}
	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}
	
	
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getParent() {
		return parent;
	}
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
	
	
	public double getSortNo() {
		return sortNo;
	}
	public void setSortNo(double sortNo) {
		this.sortNo = sortNo;
	}
	@Override
	public String toString() {
		return "AuthPrivilegeInfo [privilegeId=" + privilegeId
				+ ", privilegeName=" + privilegeName + ", privilegeType="
				+ privilegeType + ", formId=" + formId + ", operateName="
				+ operateName + ", url=" + url + ", parent=" + parent
				+ ", ifButtonGroup=" + ifButtonGroup
				+ ", ifHiddenOnTaskRunning=" + ifHiddenOnTaskRunning
				+ ", taskType=" + taskType + ", machineId=" + machineId
				+ ", enumCodeList=" + enumCodeList + ", operatorRequestType="
				+ operatorRequestType + ", javaScriptFunction="
				+ javaScriptFunction + ", stateMachineDefine="
				+ stateMachineDefine + ", form=" + form + ", row=" + row
				+ ", col=" + col + ", ifLeaf=" + ifLeaf + ", ifLevelLeaf="
				+ ifLevelLeaf + ", allChildrenRowSize=" + allChildrenRowSize
				+ "]";
	}
	
}
