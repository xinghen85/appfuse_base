package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgEnumValueInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgEnumValueInfo table.
 */
public interface CfgEnumValueInfoDao extends DAO<CfgEnumValueInfo, Long> {

public void saveMainBody(CfgEnumValueInfo a0);
}