package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="st_system_notice_info", noClassnameStored=true) 
@TableAnnoExtend(description="系统公告",textSearch=true,combinedSearch=true,parent="")
@javax.persistence.Entity
@javax.persistence.Table(name="StSystemNoticeInfo",catalog="bsquiz")
public class StSystemNoticeInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="公告ID")
	Long noticeId;
	@FieldAnnoExtend(description="公告名称",required=true,name=true,textSearch=true)
	String noticeName;
	@FieldAnnoExtend(type=1,enumCode="BT",pleaseSelect=false,description="公告类型",required=true,combinedSearch=true)
	String noticeType;
	
	@FieldAnnoExtend(type=1,enumCode="BU",pleaseSelect=false,description="公告级别",required=true,combinedSearch=true)
	String noticeLevel;
	
	/*@FieldAnnoExtend(type=1,enumCode="BU",pleaseSelect=false,description="公告范围",required=true,combinedSearch=true)
	String noticeScope;*/
	
	@FieldAnnoExtend(description="公告内容",required=true)
	String noticeText;
	
	@FieldAnnoExtend(type=9,description="角色列表",foreignModel="AuthAppRole",multiple=true)
	List<Long> roleList=new ArrayList<Long>();
	
	@FieldAnnoExtend(type=9,description="用户列表",foreignModel="AuthAppUser",multiple=true)
	List<Long> userList=new ArrayList<Long>();
	
	@FieldAnnoExtend(description="创建用户")
	Long createUserId;
	
	
	@FieldAnnoExtend(type=1,enumCode="BV",pleaseSelect=false,description="公告状态",required=true,combinedSearch=true)
	String status;
	
	Long library;
	@FieldAnnoExtend(description="是否公开",required=true)
	boolean overt;
	
	
	@FieldAnnoExtend(description="创建时间",date=true)
	Date createTime;

	
	
	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeLevel() {
		return noticeLevel;
	}

	public void setNoticeLevel(String noticeLevel) {
		this.noticeLevel = noticeLevel;
	}

	public String getNoticeText() {
		return noticeText;
	}

	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}

	public List<Long> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}

	public List<Long> getUserList() {
		return userList;
	}

	public void setUserList(List<Long> userList) {
		if(userList!=null) {
			this.userList = userList;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLibrary() {
		return library;
	}

	public void setLibrary(Long library) {
		this.library = library;
	}

	public boolean isOvert() {
		return overt;
	}

	public void setOvert(boolean overt) {
		this.overt = overt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
