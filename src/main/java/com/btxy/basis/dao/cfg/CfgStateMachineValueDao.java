package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgStateMachineValue;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgStateMachineValue table.
 */
public interface CfgStateMachineValueDao extends DAO<CfgStateMachineValue, Long> {

public void saveMainBody(CfgStateMachineValue a0);
}