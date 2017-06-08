package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgIconInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgIconInfo table.
 */
public interface CfgIconInfoDao extends DAO<CfgIconInfo, Long> {

public void saveMainBody(CfgIconInfo a0);
}