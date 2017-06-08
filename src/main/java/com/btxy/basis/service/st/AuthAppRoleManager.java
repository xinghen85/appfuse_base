package com.btxy.basis.service.st;

import com.btxy.basis.model.AuthAppRole;

import com.btxy.basis.service.base.MgGenericManager;


public interface AuthAppRoleManager extends MgGenericManager<AuthAppRole, Long> {
    public void saveMainBody(AuthAppRole a0);
}