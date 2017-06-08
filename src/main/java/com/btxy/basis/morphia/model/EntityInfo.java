package com.btxy.basis.morphia.model;

import java.io.Serializable;

public class EntityInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Integer index;
	String name;
	String cname;
	String model;
	
	
	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	boolean ifQuestionEntiry=false;
	String question;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCname() {
		return cname;
	}


	public void setCname(String cname) {
		this.cname = cname;
	}


	

	public boolean isIfQuestionEntiry() {
		return ifQuestionEntiry;
	}


	public void setIfQuestionEntiry(boolean ifQuestionEntiry) {
		this.ifQuestionEntiry = ifQuestionEntiry;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAb() {
		return ab;
	}


	public void setAb(String ab) {
		this.ab = ab;
	}


	String ab;
	
}
