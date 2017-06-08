package com.btxy.basis.service.cfg;

import com.btxy.basis.model.AuthPrivilegeInfo;

import java.util.List;

import javax.jws.WebService;

import com.btxy.basis.service.base.MgGenericManager;


public interface AuthPrivilegeInfoManager extends MgGenericManager<AuthPrivilegeInfo, Long> {
    public void saveMainBody(AuthPrivilegeInfo a0);
    public void rebuildChildPivilege(AuthPrivilegeInfo authPrivilegeInfo);
	public void saveMove(Long parentId, Long privilegeId, Integer moveType);
}