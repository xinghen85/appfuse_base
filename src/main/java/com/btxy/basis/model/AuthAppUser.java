package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.utils.IndexDirection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(value="auth_app_user", noClassnameStored=true) 
@TableAnnoExtend(description="系统用户",textSearch=true,combinedSearch=false,parent="")
@javax.persistence.Entity
@javax.persistence.Table(name="AuthAppUser",catalog="bsquiz")
public class AuthAppUser implements AuthUser,Serializable, UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="用户ID")
	protected Long userId;
	@FieldAnnoExtend(description="用户名",required=true)
	@Indexed(value=IndexDirection.ASC, name="idx_user_username", unique=true, dropDups=true,sparse = true) 
	protected String userName;
	@FieldAnnoExtend(description="用户姓名",required=true,textSearch=true,name=true,minlength=1,maxlength=20)
	protected String fullName;
	@FieldAnnoExtend(description="用户密码",required=true,minlength=6,maxlength=20)
	protected String password;
	@FieldAnnoExtend(description="用户密码提示",required=true,minlength=6,maxlength=20)
	protected String passwordHint;
	@FieldAnnoExtend(description="用户邮箱",required=true,textSearch=true,email=true)
	@Indexed(value=IndexDirection.ASC, name="idx_user_email", unique=true, dropDups=true) 
	protected String email;
	@FieldAnnoExtend(description="手机号码",textSearch=true,digits=true,minlength=11,maxlength=11)
	@Indexed(value=IndexDirection.ASC, name="idx_user_phonenumber", unique=true, dropDups=true,sparse = true) 
	protected Long phoneNumber;
	@FieldAnnoExtend(description="是否激活",required=true)
	protected boolean enabled;
	
	
	@FieldAnnoExtend(description="所属业务",required=true)
	protected String businessIds;
	@FieldAnnoExtend(description="所属频道",required=true)
	protected String channelIds;
	@FieldAnnoExtend(description="内容源类型",required=true)
	 protected String contentSourceType;
	
	
	public String getContentSourceType() {
		return contentSourceType;
	}
	public void setContentSourceType(String contentSourceType) {
		this.contentSourceType = contentSourceType;
	}
	@FieldAnnoExtend(type=5,childModel="AuthUserLibraryRole")
	List<AuthUserLibraryRole> libraryRoleList=new ArrayList<AuthUserLibraryRole>();
	
	protected Date createTime;
	protected Date updateTime;
	
	
	@FieldAnnoExtend(description="库",required=true)
	Long library;
	@FieldAnnoExtend(description="是否公开",required=true)
	boolean overt;
	
	@Transient
	@javax.persistence.Transient
	String oldPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public Long getDefaultLibrary(){
		for(AuthUserLibraryRole rid:libraryRoleList){
			if(rid.ifDefault){
				return rid.getLibraryId();
			}
		}
		return library;
	}
	public List<Long> getRoleList(Long library) {
		List<Long> list=new ArrayList<Long>();
		for(AuthUserLibraryRole rid:libraryRoleList){
			if(rid.getLibraryId()==library){
				list.addAll(rid.getRoleList());
			}
		}
		return list;
	}
	public List<Long> getServerReportList(Long library) {
		return getList(library,TYPE.ReportServer);
	}
	public List<Long> getAdminReportList(Long library) {
		return getList(library,TYPE.AdminServer);
	}
	enum TYPE{AdminServer,ReportServer};
	private List<Long> getList(Long library,TYPE type) {
		List<Long> list=new ArrayList<Long>();
		for(AuthUserLibraryRole rid:libraryRoleList){
			List<Long> adminServerList = rid.getAdminServerList();
			if(type==TYPE.ReportServer){
				adminServerList=rid.getReportServerList();
			}
			if(rid.getLibraryId().longValue()==library.longValue()){
				for(Long ridl:adminServerList){
					if(!list.contains(ridl)){
						list.add(ridl);
					}
				}
			}
		}
		return list;
	}
	
	public List<AuthUserLibraryRole> getLibraryRoleList() {
		return libraryRoleList;
	}

	public void setLibraryRoleList(List<AuthUserLibraryRole> libraryRoleList) {
		if(libraryRoleList!=null){
			this.libraryRoleList = libraryRoleList;
		}
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	

	@Override
	public Set<GrantedAuthority>  getAuthorities() {
		Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
		for(AuthUserLibraryRole rid:libraryRoleList){
			for(Long ridl:rid.getRoleList()){
				AuthAppRole ar=new AuthAppRole();
				ar.setRoleId(ridl);
				authorities.add(ar);
			}
		}
        return authorities;
 	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}

	public String getBusinessIds() {
		return businessIds;
	}
	public void setBusinessIds(String businessIds) {
		this.businessIds = businessIds;
	}

	
	public String getChannelIds() {
		return channelIds;
	}
	public void setChannelIds(String channelIds) {
		this.channelIds = channelIds;
	}
	@Override
	public String toString() {
		return "AuthAppUser [userId=" + userId + ", userName=" + userName
				+ ", fullName=" + fullName + ", password=" + password
				+ ", passwordHint=" + passwordHint + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", enabled=" + enabled
				+ ", roleList=" + this.libraryRoleList + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
}
