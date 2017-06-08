package com.btxy.basis.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchConditionValue implements Serializable{
	/**
	 * 
	 */
	
		
	private static final long serialVersionUID = 1L;
	String textValue;
	Map<String,Object> combinedConditionValue=new HashMap<String,Object>();
	Map<String,Object> customPropertyValue=new HashMap<String,Object>();
	
	Map<String,List<Long>> combinedConditionValueOfLongList=new HashMap<String,List<Long>>();
	Map<String,List<String>> combinedConditionValueOfStringList=new HashMap<String,List<String>>();
	public String getTextValue() {
		return textValue;
	}
	public void setTextValue(String textValue) {
		if(!"".equals(textValue) ){
			this.textValue = textValue;
		}
	}
	public Map<String, Object> getCombinedConditionValue() {
		return combinedConditionValue;
	}
	public void setCombinedConditionValue(Map<String, Object> combinedConditionValue) {
		this.combinedConditionValue = combinedConditionValue;
	}
	public Map<String, Object> getCustomPropertyValue() {
		return customPropertyValue;
	}
	public void setCustomPropertyValue(Map<String, Object> customPropertyValue) {
		this.customPropertyValue = customPropertyValue;
	}
	public Map<String, List<Long>> getCombinedConditionValueOfLongList() {
		return combinedConditionValueOfLongList;
	}
	public void setCombinedConditionValueOfLongList(
			Map<String, List<Long>> combinedConditionValueOfLongList) {
		this.combinedConditionValueOfLongList = combinedConditionValueOfLongList;
	}
	public Map<String, List<String>> getCombinedConditionValueOfStringList() {
		return combinedConditionValueOfStringList;
	}
	public void setCombinedConditionValueOfStringList(
			Map<String, List<String>> combinedConditionValueOfStringList) {
		this.combinedConditionValueOfStringList = combinedConditionValueOfStringList;
	}
	
	
}
