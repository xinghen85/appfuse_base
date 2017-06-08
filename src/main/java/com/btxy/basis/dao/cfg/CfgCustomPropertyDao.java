package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgCustomProperty;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgCustomProperty table.
 */
public interface CfgCustomPropertyDao extends DAO<CfgCustomProperty, Long> {

public void saveMainBody(CfgCustomProperty a0);
}