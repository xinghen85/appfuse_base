package com.btxy.basis.model.jspvo;

public class LabelVO {
	public LabelVO(){
		
	}
	public LabelVO(String id1,String text1){
		this.id=id1;
		this.text=text1;
	}
	String id;
	String text;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
