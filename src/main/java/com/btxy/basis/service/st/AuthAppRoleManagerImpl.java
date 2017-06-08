package com.btxy.basis.service.st;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btxy.basis.dao.st.AuthAppRoleDao;
import com.btxy.basis.model.AuthAppRole;
import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("authAppRoleManager")
public class AuthAppRoleManagerImpl extends MgGenericManagerImpl<AuthAppRole, Long> implements AuthAppRoleManager {
    AuthAppRoleDao authAppRoleDao;

    @Autowired
    public AuthAppRoleManagerImpl(AuthAppRoleDao authAppRoleDao) {
        super(authAppRoleDao);
        this.authAppRoleDao = authAppRoleDao;
    }
    @Override
	public void saveMainBody(AuthAppRole authAppRole) {
		this.authAppRoleDao.saveMainBody(authAppRole);
	}
}