package com.btxy.basis.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TreeEnumObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long id;
	
	List<Long> ids=new ArrayList<Long>();
	
	String fullId;
	
	
	public List<Long> getIds() {
		return ids;
	}
	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	public String getFullId() {
		return fullId;
	}
	public void setFullId(String fullId) {
		this.fullId = fullId;
		
		if(fullId!=null){
			String[] a=fullId.split("-");
			if(a!=null){
				for(int i=0;i<a.length;i++){
					if(i==(a.length-1)){
						id=new Long(a[i]);
					}
					ids=new ArrayList<Long>();
					ids.add(new Long(a[i]));
				}
			}
		}		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}	
