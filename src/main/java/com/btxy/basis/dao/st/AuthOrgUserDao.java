package com.btxy.basis.dao.st;


import com.btxy.basis.model.AuthOrgUser;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the AuthOrgUser table.
 */
public interface AuthOrgUserDao extends DAO<AuthOrgUser, Long> {

public void saveMainBody(AuthOrgUser a0);
}