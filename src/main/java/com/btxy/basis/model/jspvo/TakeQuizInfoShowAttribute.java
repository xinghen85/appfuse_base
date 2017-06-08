package com.btxy.basis.model.jspvo;

public class TakeQuizInfoShowAttribute {
	private String name;
	private String title;
	private String valueType;
	private String type;
	private Long fixedPropertyDefineId;
	private String fixedPropertyCode;
	
	
	public String getFixedPropertyCode() {
		return fixedPropertyCode;
	}
	public void setFixedPropertyCode(String fixedPropertyCode) {
		this.fixedPropertyCode = fixedPropertyCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getFixedPropertyDefineId() {
		return fixedPropertyDefineId;
	}
	public void setFixedPropertyDefineId(Long fixedPropertyDefineId) {
		this.fixedPropertyDefineId = fixedPropertyDefineId;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}
