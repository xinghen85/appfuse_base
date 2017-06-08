package com.btxy.basis.morphia.model;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Embedded;

@Embedded

public class CatalogInfo implements Serializable{


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer paperCatalogInfoId;
    
     private String catalogInfoName;
    
     private String catalogFullName;
     private Integer parentCatalog;
     
	public Integer getPaperCatalogInfoId() {
		return paperCatalogInfoId;
	}
	public void setPaperCatalogInfoId(Integer paperCatalogInfoId) {
		this.paperCatalogInfoId = paperCatalogInfoId;
	}
	public String getCatalogInfoName() {
		return catalogInfoName;
	}
	public void setCatalogInfoName(String catalogInfoName) {
		this.catalogInfoName = catalogInfoName;
	}
	public String getCatalogFullName() {
		return catalogFullName;
	}
	public void setCatalogFullName(String catalogFullName) {
		this.catalogFullName = catalogFullName;
	}
	public Integer getParentCatalog() {
		return parentCatalog;
	}
	public void setParentCatalog(Integer parentCatalog) {
		this.parentCatalog = parentCatalog;
	}




}