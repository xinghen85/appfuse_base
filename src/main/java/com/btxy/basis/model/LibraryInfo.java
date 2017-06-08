package com.btxy.basis.model;

import java.io.Serializable;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.common.util.SequenceUtil;

@Entity(value="cfg_library_info", noClassnameStored=true) 
@TableAnnoExtend(description="系统用户",textSearch=true,combinedSearch=false,parent="")
@javax.persistence.Entity
@javax.persistence.Table(name="LibraryInfo",catalog="bsquiz")
public class LibraryInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3595299476929745026L;

	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="数据库ID")
	private Long libraryId;
	
	@FieldAnnoExtend(description="数据库名称",required=true,textSearch=true,name=true,minlength=1,maxlength=20)
	private String libraryName;
	
	@FieldAnnoExtend(description="数据库路径",required=true,minlength=1,maxlength=20)
	private String path;
	@FieldAnnoExtend(description="数据库标识",required=true,minlength=1,maxlength=20)
	
	private String identification;
	public Long getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(Long libraryId) {
		this.libraryId = libraryId;
	}
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public static void main(String[] args) {
		Datastore ds=SpringContext.getDatastore();
		LibraryInfo li=new LibraryInfo();
		
		li.setLibraryId(SequenceUtil.getNext(LibraryInfo.class));
		li.setPath("default");
		li.setIdentification("default");
		li.setLibraryName("缺省库");
		ds.save(li);
	}
}
