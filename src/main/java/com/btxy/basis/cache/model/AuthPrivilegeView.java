package com.btxy.basis.cache.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.btxy.basis.model.AuthPrivilegeInfo;

public class AuthPrivilegeView implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AuthPrivilegeInfo authPrivilegeInfo;
	
	List<AuthPrivilegeView> childen=new ArrayList<AuthPrivilegeView>();
	List<AuthPrivilegeView> leafChilden=new ArrayList<AuthPrivilegeView>();
	List<AuthPrivilegeView> notLeafChilden=new ArrayList<AuthPrivilegeView>();
	
	
	public List<AuthPrivilegeView> getNotLeafChilden() {
		return notLeafChilden;
	}

	public void setNotLeafChilden(List<AuthPrivilegeView> notLeafChilden) {
		this.notLeafChilden = notLeafChilden;
	}

	public List<AuthPrivilegeView> getLeafChilden() {
		return leafChilden;
	}

	public void setLeafChilden(List<AuthPrivilegeView> leafChilden) {
		this.leafChilden = leafChilden;
	}

	public List<AuthPrivilegeView> getChilden() {
		return childen;
	}

	public void setChilden(List<AuthPrivilegeView> childen) {
		this.childen = childen;
	}

	public AuthPrivilegeInfo getAuthPrivilegeInfo() {
		return authPrivilegeInfo;
	}

	public void setAuthPrivilegeInfo(AuthPrivilegeInfo authPrivilegeInfo) {
		this.authPrivilegeInfo = authPrivilegeInfo;
	}
	
}