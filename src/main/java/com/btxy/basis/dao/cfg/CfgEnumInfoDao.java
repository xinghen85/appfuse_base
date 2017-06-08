package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgEnumInfo;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgEnumInfo table.
 */
public interface CfgEnumInfoDao extends DAO<CfgEnumInfo, Long> {

public void saveMainBody(CfgEnumInfo a0);
}