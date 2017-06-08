package com.btxy.basis.common.model;

import java.io.Serializable;
import java.util.Map;

import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.AuthUser;

public class CurrentUserPrivilegeMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthUser user;
	
	public Map<String,Boolean> privilegeMap;
	
	public CurrentUserPrivilegeMap(){
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public AuthUser getUser() {
		return user;
	}
	public void setUser(AuthUser user) {
		this.user = user;
	}
	public Map<String, Boolean> getPrivilegeMap() {
		return privilegeMap;
	}
	public void setPrivilegeMap(Map<String, Boolean> privilegeMap) {
		this.privilegeMap = privilegeMap;
	}

}
