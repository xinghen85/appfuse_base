package com.btxy.basis.dao.st;


import com.btxy.basis.model.FileInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the FileInfo table.
 */
public interface FileInfoDao extends DAO<FileInfo, Long> {

public void saveMainBody(FileInfo a0);
}