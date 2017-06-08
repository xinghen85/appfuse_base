package com.btxy.basis.service.cfg;

import com.btxy.basis.model.AuthShortCutPrivilege;


import com.btxy.basis.service.base.MgGenericManager;


public interface AuthShortCutPrivilegeManager extends MgGenericManager<AuthShortCutPrivilege, Long> {
    public void saveMainBody(AuthShortCutPrivilege a0);
}