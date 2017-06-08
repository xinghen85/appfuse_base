package com.btxy.basis.model;

import java.util.List;

public interface AuthUser {
	public Long getDefaultLibrary();
	public List<Long> getRoleList(Long library);
	public List<Long> getServerReportList(Long library);
	public List<Long> getAdminReportList(Long library);
	public List<AuthUserLibraryRole> getLibraryRoleList();
	public Long getLibrary();
	public boolean isOvert();
	public Long getUserId();
	public String getUserName();
	public String getFullName() ;
	public String getUsername();
}
