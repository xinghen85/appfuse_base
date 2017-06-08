package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgParameter;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgParameter table.
 */
public interface CfgParameterDao extends DAO<CfgParameter, Long> {

public void saveMainBody(CfgParameter a0);
}