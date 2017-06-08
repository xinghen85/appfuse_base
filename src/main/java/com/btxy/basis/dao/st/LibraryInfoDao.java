package com.btxy.basis.dao.st;


import com.btxy.basis.model.LibraryInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the LibraryInfo table.
 */
public interface LibraryInfoDao extends DAO<LibraryInfo, Long> {

public void saveMainBody(LibraryInfo a0);
}