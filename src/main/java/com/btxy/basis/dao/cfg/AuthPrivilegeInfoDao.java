package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.AuthPrivilegeInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the AuthPrivilegeInfo table.
 */
public interface AuthPrivilegeInfoDao extends DAO<AuthPrivilegeInfo, Long> {

public void saveMainBody(AuthPrivilegeInfo a0);
}