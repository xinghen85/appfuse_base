package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgFormInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgFormInfo table.
 */
public interface CfgFormInfoDao extends DAO<CfgFormInfo, Long> {

public void saveMainBody(CfgFormInfo a0);
}