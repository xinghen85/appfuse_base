package com.btxy.basis.morphia.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class SelectOptionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer selectOptionIndex;
	String contentText;
	Double score;
	
	public Integer getSelectOptionIndex() {
		return selectOptionIndex;
	}
	public void setSelectOptionIndex(Integer selectOptionIndex) {
		this.selectOptionIndex = selectOptionIndex;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
}
