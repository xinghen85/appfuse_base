package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.AuthShortCutPrivilegeDao;
import com.btxy.basis.model.AuthShortCutPrivilege;
import com.btxy.basis.service.cfg.AuthShortCutPrivilegeManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("authShortCutPrivilegeManager")
public class AuthShortCutPrivilegeManagerImpl extends MgGenericManagerImpl<AuthShortCutPrivilege, Long> implements AuthShortCutPrivilegeManager {
    AuthShortCutPrivilegeDao authShortCutPrivilegeDao;

    @Autowired
    public AuthShortCutPrivilegeManagerImpl(AuthShortCutPrivilegeDao authShortCutPrivilegeDao) {
        super(authShortCutPrivilegeDao);
        this.authShortCutPrivilegeDao = authShortCutPrivilegeDao;
    }
    @Override
	public void saveMainBody(AuthShortCutPrivilege authShortCutPrivilege) {
		this.authShortCutPrivilegeDao.saveMainBody(authShortCutPrivilege);
	}
}