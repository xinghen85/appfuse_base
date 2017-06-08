package com.btxy.basis.dao.st;

import org.mongodb.morphia.dao.DAO;

import com.btxy.basis.model.AuthAppUser;

/**
 * An interface that provides a data management interface to the AuthAppUser
 * table.
 */
public interface AuthAppUserDao extends DAO<AuthAppUser, Long> {

	public void saveMainBody(AuthAppUser a0);
}