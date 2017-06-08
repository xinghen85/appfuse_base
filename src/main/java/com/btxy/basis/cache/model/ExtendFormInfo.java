package com.btxy.basis.cache.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.appfuse.anno.AnnoInfo;

import com.btxy.basis.model.CfgFormInfo;

public class ExtendFormInfo {
	
	public Long getFormId(){
		return formInfo.getFormId();
	}
	public ExtendFormInfo(CfgFormInfo formInfo1){
		formInfo=formInfo1;
	}
	
	CfgFormInfo formInfo;
	
	
	Class<?> formClass;
	
	
	Method nameGetMethod;
	
	
	Method idGetMethod;
	
	
	List<AnnoInfo> fieldAnnoInfoList;
	
	
	
	Field nameField;
	
	
	Field idField;


	public CfgFormInfo getFormInfo() {
		return formInfo;
	}


	public void setFormInfo(CfgFormInfo formInfo) {
		this.formInfo = formInfo;
	}


	public Class<?> getFormClass() {
		return formClass;
	}


	public void setFormClass(Class<?> formClass) {
		this.formClass = formClass;
	}


	public Method getNameGetMethod() {
		return nameGetMethod;
	}


	public void setNameGetMethod(Method nameGetMethod) {
		this.nameGetMethod = nameGetMethod;
	}


	public Method getIdGetMethod() {
		return idGetMethod;
	}


	public void setIdGetMethod(Method idGetMethod) {
		this.idGetMethod = idGetMethod;
	}


	public List<AnnoInfo> getFieldAnnoInfoList() {
		return fieldAnnoInfoList;
	}


	public void setFieldAnnoInfoList(List<AnnoInfo> fieldAnnoInfoList) {
		this.fieldAnnoInfoList = fieldAnnoInfoList;
	}


	public Field getNameField() {
		return nameField;
	}


	public void setNameField(Field nameField) {
		this.nameField = nameField;
	}


	public Field getIdField() {
		return idField;
	}


	public void setIdField(Field idField) {
		this.idField = idField;
	}
	
	
}
