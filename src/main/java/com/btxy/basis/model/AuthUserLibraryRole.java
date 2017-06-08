package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="auth_user_library_role", noClassnameStored=true) 
@TableAnnoExtend(description="用户库角色",textSearch=true,combinedSearch=false,parent="AuthAppUser")
@javax.persistence.Entity
@javax.persistence.Table(name="AuthUserLibraryRole",catalog="bsquiz")
public class AuthUserLibraryRole implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3690197650654049848L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="库角色ID")
	private Long libraryRoleId;
	@FieldAnnoExtend(description="描述")
	private String description;
	@FieldAnnoExtend(type=9,description="角色列表",foreignModel="AuthAppRole",multiple=true)
	List<Long> roleList=new ArrayList<Long>();
	@FieldAnnoExtend(type=9,description="报告服务端",foreignModel="StReportServerInfo",multiple=true)
	List<Long> reportServerList=new ArrayList<Long>();
	@FieldAnnoExtend(type=9,description="管理服务端",foreignModel="StAdminServerInfo",multiple=true)
	List<Long> adminServerList=new ArrayList<Long>();
	@FieldAnnoExtend(description="是否默认",required=true)
	boolean ifDefault;
	@FieldAnnoExtend(type=9,description="数据库",foreignModel="LibraryInfo",required=true)
	Long libraryId;
	@FieldAnnoExtend(description="是否公开",required=true)
	boolean overt;
	
	
	
	public boolean isIfDefault() {
		return ifDefault;
	}
	public void setIfDefault(boolean ifDefault) {
		this.ifDefault = ifDefault;
	}
	public Long getLibraryRoleId() {
		return libraryRoleId;
	}
	public void setLibraryRoleId(Long libraryRoleId) {
		this.libraryRoleId = libraryRoleId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Long> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Long> roleList) {
		if(roleList==null){
			roleList=new ArrayList<Long>();
		}
		this.roleList = roleList;
	}
	
	public List<Long> getAdminServerList() {
		return adminServerList;
	}
	public void setAdminServerList(List<Long> adminServerList) {
		if(adminServerList==null){
			adminServerList=new ArrayList<Long>();
		}
		this.adminServerList = adminServerList;
	}
	public List<Long> getReportServerList() {
		return reportServerList;
	}
	public void setReportServerList(List<Long> reportServerList) {
		this.reportServerList = reportServerList;
	}
	public Long getLibraryId() {
		return libraryId;
	}
	public void setLibraryId(Long libraryId) {
		this.libraryId = libraryId;
	}
	public boolean isOvert() {
		return overt;
	}
	public void setOvert(boolean overt) {
		this.overt = overt;
	}
	
	
}
