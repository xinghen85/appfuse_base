package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.AuthShortCutPrivilege;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the AuthShortCutPrivilege table.
 */
public interface AuthShortCutPrivilegeDao extends DAO<AuthShortCutPrivilege, Long> {

public void saveMainBody(AuthShortCutPrivilege a0);
}