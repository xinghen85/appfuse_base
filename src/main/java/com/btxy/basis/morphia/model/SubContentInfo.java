package com.btxy.basis.morphia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.appfuse.anno.FieldAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;


@Entity(value="test_sub_content", noClassnameStored=true) 
@javax.persistence.Entity
@javax.persistence.Table(name="SubContentInfo",catalog="bsquiz")
public class SubContentInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	private Long subContentId;
	
	@FieldAnnoExtend(required=true,maxlength=10)
	private String subContentName;
	
	@FieldAnnoExtend(required=true,type=1,enumCode="1A")
	private String contentType;

	public Long getSubContentId() {
		return subContentId;
	}

	public void setSubContentId(Long subContentId) {
		this.subContentId = subContentId;
	}

	public String getSubContentName() {
		return subContentName;
	}

	public void setSubContentName(String subContentName) {
		this.subContentName = subContentName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	
	
}
