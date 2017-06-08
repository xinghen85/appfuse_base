package com.btxy.basis.common.model;

import java.io.Serializable;

public class MulitiEnumObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String fullId;
	
	Long id;
	Long parentId;
	
	public String getFullId() {
		return fullId;
	}
	public void setFullId(String fullId) {
		this.fullId = fullId;
		if(fullId!=null){
			String[] a=fullId.split("-");
			//try{
				if(a!=null && a.length>0){
					parentId=new Long(a[0]);
				}
				if(a!=null && a.length>1){
					id=new Long(a[1]);
				}
			//}catch(Exception e){
			//	e.printStackTrace();
			//}
		}
		
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
	
	
}
