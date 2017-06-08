package com.btxy.basis.dao.cfg;


import com.btxy.basis.model.CfgStateMachineDefine;
import org.mongodb.morphia.dao.DAO;


/**
 * An interface that provides a data management interface to the CfgStateMachineDefine table.
 */
public interface CfgStateMachineDefineDao extends DAO<CfgStateMachineDefine, Long> {

public void saveMainBody(CfgStateMachineDefine a0);
}