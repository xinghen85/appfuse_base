package com.btxy.basis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.appfuse.anno.FieldAnnoExtend;
import org.appfuse.anno.TableAnnoExtend;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.springframework.security.core.GrantedAuthority;

@Entity(value="auth_app_role", noClassnameStored=true) 
@TableAnnoExtend(description="系统角色",textSearch=true,combinedSearch=false,parent="")
@javax.persistence.Entity
@javax.persistence.Table(name="AuthAppRole",catalog="bsquiz")
public class AuthAppRole implements Serializable,GrantedAuthority{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3690197650654049848L;
	@Id
	@javax.persistence.Id
	@FieldAnnoExtend(description="角色ID")
	private Long roleId;
	@FieldAnnoExtend(description="角色名",required=true,name=true)
	private String roleName;
	@FieldAnnoExtend(description="角色描述")
	private String description;
	List<Long> privilegeList=new ArrayList<Long>();
	@FieldAnnoExtend(description="库",required=true)
	Long library;
	@FieldAnnoExtend(description="是否公开",required=true)
	boolean overt;
	
	
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
	public List<Long> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<Long> privilegeList) {
		if(privilegeList!=null){
			this.privilegeList = privilegeList;
		}
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return getRoleId().toString();
	}
	
	
	
}
