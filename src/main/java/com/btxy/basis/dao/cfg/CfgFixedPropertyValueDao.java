package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgFixedPropertyValue;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgFixedPropertyValue table.
 */
public interface CfgFixedPropertyValueDao extends DAO<CfgFixedPropertyValue, Long> {

public void saveMainBody(CfgFixedPropertyValue a0);
}