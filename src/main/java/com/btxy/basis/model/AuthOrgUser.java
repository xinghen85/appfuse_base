package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.json.JSONException;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.utils.IndexDirection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(value="auth_org_user", noClassnameStored=true) 
@TableAnnoExtend(description="用户",textSearch=false,combinedSearch=true,parent="")
@javax.persistence.Entity
@javax.persistence.Table(name="AuthOrgUser",catalog="bsquiz")
public class AuthOrgUser implements AuthUser,Serializable, UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="用户ID")
	protected Long userId;
	@FieldAnnoExtend(description="用户名",textSearch=true,required=true)
	@Indexed(value=IndexDirection.ASC, name="idx_user_username", unique=true, dropDups=true,sparse = true) 
	protected String userName;
	@FieldAnnoExtend(description="用户姓名",required=true,textSearch=true,name=true,minlength=1,maxlength=20)
	protected String fullName;
	
	@FieldAnnoExtend(description="用户密码",required=true,minlength=6,maxlength=20)
	protected String password;
	
	@FieldAnnoExtend(description="用户密码提示",required=true,minlength=6,maxlength=20)
	protected String passwordHint;
	@FieldAnnoExtend(description="用户邮箱",textSearch=true,email=true)
	@Indexed(value=IndexDirection.ASC, name="idx_user_email", unique=true, dropDups=true,sparse = true) 
	protected String email;

	@FieldAnnoExtend(description="手机号码",textSearch=true,digits=true,minlength=11,maxlength=11)
	@Indexed(value=IndexDirection.ASC, name="idx_user_phonenumber", unique=true, dropDups=true,sparse = true) 
	protected Long phoneNumber;
	
	@FieldAnnoExtend(description="是否激活",required=true)
	protected boolean enabled;

	/*@FieldAnnoExtend(type=6,description="角色列表",foreignModel="AuthAppRole")*/
	@FieldAnnoExtend(type=5,childModel="AuthUserLibraryRole")
	List<AuthUserLibraryRole> libraryRoleList=new ArrayList<AuthUserLibraryRole>();
	
	
	
	protected Date createTime;
	
	protected Date updateTime;
	
	
	@FieldAnnoExtend(type=9,description="基础对象定义",foreignModel="DmObjectInfo",combinedSearch=true)
	Long objectId;
	
	@FieldAnnoExtend(type=9,description="基础对象模型",foreignModel="DmObjectModelInfo",combinedSearch=true)
	Long objectModelId;;
	
	
	
	@FieldAnnoExtend(description="库",required=true)
	Long library;
	@FieldAnnoExtend(description="是否公开",required=true)
	boolean overt;
	
	@Transient
	@javax.persistence.Transient
	String oldPassword;
	
	@FieldAnnoExtend(type=9,description="角色列表",foreignModel="AuthAppRole",multiple=true,combinedSearch=true)
	List<Long> roleList=new ArrayList<Long>();
	
	@FieldAnnoExtend(description="对象实例ID",required=true)
	Long objectInstanceId;
	

	public Long getObjectInstanceId() {
		return objectInstanceId;
	}
	public void setObjectInstanceId(Long objectInstanceId) {
		this.objectInstanceId = objectInstanceId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Long getObjectModelId() {
		return objectModelId;
	}
	public void setObjectModelId(Long objectModelId) {
		this.objectModelId = objectModelId;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public List<Long> getServerReportList(Long library) {
		List<Long> list=new ArrayList<Long>();
		if(libraryRoleList!=null){
			for(AuthUserLibraryRole rid:libraryRoleList){
				if(rid.getReportServerList()!=null && rid.getLibraryId().longValue()==library.longValue()){
					for(Long ridl:rid.getReportServerList()){
						if(!list.contains(ridl)){
							list.add(ridl);
						}
					}
					
				}
				
			}
		}
		return list;
	}
	public List<Long> getAdminReportList(Long library) {
		List<Long> list=new ArrayList<Long>();
		if(libraryRoleList!=null){
			for(AuthUserLibraryRole rid:libraryRoleList){
				if(rid.getAdminServerList()!=null && rid.getLibraryId().longValue()==library.longValue()){
					for(Long ridl:rid.getAdminServerList()){
						if(!list.contains(ridl)){
							list.add(ridl);
						}
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
		// TODO Auto-generated method stub
		Set<GrantedAuthority> authorities = new LinkedHashSet<GrantedAuthority>();
		if(libraryRoleList!=null){
			for(AuthUserLibraryRole rid:libraryRoleList){
				if(rid.getRoleList()!=null){
					for(Long ridl:rid.getRoleList()){
						AuthAppRole ar=new AuthAppRole();
						ar.setRoleId(ridl);
						authorities.add(ar);
					}
					
				}
				
			}
		}
        
        return authorities;
 	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getPasswordHint() {
		return passwordHint;
	}

	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
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
	
	
	
	
	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		
		
	}


	public List<Long> getRoleList() {
		return roleList;
	}


	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}
	
	public Long getDefaultLibrary(){
		return library;
	}
	public List<Long> getRoleList(Long library) {
		return roleList;
	}
	
}
