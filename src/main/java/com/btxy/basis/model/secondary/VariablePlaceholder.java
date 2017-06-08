package com.btxy.basis.model.secondary;

import java.io.Serializable;
import java.util.List;

public class VariablePlaceholder implements Serializable {
	String rootKey;
	String text;
	String realText;
	List<String> variableArray;

	Long functionInstanceId;
	Long functionDefineId;
	String funType;

	// return attribute
	Long returnId;
	String returnName;
	String returnCode;
	String returnType;

	String freemarkerVariableString;

	public String getFreemarkerVariableString() {
		return freemarkerVariableString;
	}

	public void setFreemarkerVariableString(String freemarkerVariableString) {
		this.freemarkerVariableString = freemarkerVariableString;
	}

	public Long getFunctionInstanceId() {
		return functionInstanceId;
	}

	public void setFunctionInstanceId(Long functionInstanceId) {
		this.functionInstanceId = functionInstanceId;
	}

	public String getRootKey() {
		return rootKey;
	}

	public void setRootKey(String rootKey) {
		this.rootKey = rootKey;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRealText() {
		return realText;
	}

	public void setRealText(String realText) {
		this.realText = realText;
	}

	public List<String> getVariableArray() {
		return variableArray;
	}

	public void setVariableArray(List<String> variableArray) {
		this.variableArray = variableArray;
	}

	public Long getFunctionDefineId() {
		return functionDefineId;
	}

	public void setFunctionDefineId(Long functionDefineId) {
		this.functionDefineId = functionDefineId;
	}

	public String getFunType() {
		return funType;
	}

	public void setFunType(String funType) {
		this.funType = funType;
	}

	public Long getReturnId() {
		return returnId;
	}

	public void setReturnId(Long returnId) {
		this.returnId = returnId;
	}

	public String getReturnName() {
		return returnName;
	}

	public void setReturnName(String returnName) {
		this.returnName = returnName;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7715501802235519123L;

}
