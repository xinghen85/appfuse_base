package com.btxy.basis.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatDataSearchConditionValue implements Serializable{
	/**
	 * 
	 */
	
		
	private static final long serialVersionUID = 1L;
	String textValue;
	
	//维度及所辖题目 维度 题目
	String viewType;
	List<Long> catalogIdList;
	List<Long> paperQuestionIdList;
	
	Long catalogCaculateCode;
	Long questionCaculateCode;
	
	List<Long> itemCaculateCode;
	
	
	Map<String,Object> combinedConditionValue=new HashMap<String,Object>();

	

	public Long getCatalogCaculateCode() {
		return catalogCaculateCode;
	}

	public void setCatalogCaculateCode(Long catalogCaculateCode) {
		this.catalogCaculateCode = catalogCaculateCode;
	}

	public Long getQuestionCaculateCode() {
		return questionCaculateCode;
	}

	public void setQuestionCaculateCode(Long questionCaculateCode) {
		this.questionCaculateCode = questionCaculateCode;
	}

	public List<Long> getItemCaculateCode() {
		return itemCaculateCode;
	}

	public void setItemCaculateCode(List<Long> itemCaculateCode) {
		this.itemCaculateCode = itemCaculateCode;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public List<Long> getCatalogIdList() {
		return catalogIdList;
	}

	public void setCatalogIdList(List<Long> catalogIdList) {
		this.catalogIdList = catalogIdList;
	}

	public List<Long> getPaperQuestionIdList() {
		return paperQuestionIdList;
	}

	public void setPaperQuestionIdList(List<Long> paperQuestionIdList) {
		this.paperQuestionIdList = paperQuestionIdList;
	}

	public Map<String, Object> getCombinedConditionValue() {
		return combinedConditionValue;
	}

	public void setCombinedConditionValue(Map<String, Object> combinedConditionValue) {
		this.combinedConditionValue = combinedConditionValue;
	}

	@Override
	public String toString() {
		return "StatDataSearchConditionValue [textValue=" + textValue
				+ ", viewType=" + viewType + ", catalogIdList=" + catalogIdList
				+ ", paperQuestionIdList=" + paperQuestionIdList
				+ ", catalogCaculateCode=" + catalogCaculateCode
				+ ", questionCaculateCode=" + questionCaculateCode
				+ ", itemCaculateCode=" + itemCaculateCode
				+ ", combinedConditionValue=" + combinedConditionValue + "]";
	}
	
	
	
	
}
