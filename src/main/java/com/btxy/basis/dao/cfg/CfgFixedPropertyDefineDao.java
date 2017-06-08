package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgFixedPropertyDefine;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgFixedPropertyDefine table.
 */
public interface CfgFixedPropertyDefineDao extends DAO<CfgFixedPropertyDefine, Long> {

public void saveMainBody(CfgFixedPropertyDefine a0);
}