package com.btxy.basis.dao.st;


import com.btxy.basis.model.AuthAppRole;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the AuthAppRole table.
 */
public interface AuthAppRoleDao extends DAO<AuthAppRole, Long> {

public void saveMainBody(AuthAppRole a0);
}