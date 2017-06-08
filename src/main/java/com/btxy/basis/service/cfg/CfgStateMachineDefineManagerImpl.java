package com.btxy.basis.service.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btxy.basis.dao.cfg.CfgStateMachineDefineDao;
import com.btxy.basis.model.CfgStateMachineDefine;
import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgStateMachineDefineManager")
public class CfgStateMachineDefineManagerImpl extends MgGenericManagerImpl<CfgStateMachineDefine, Long> implements CfgStateMachineDefineManager {
    CfgStateMachineDefineDao cfgStateMachineDefineDao;

    @Autowired
    public CfgStateMachineDefineManagerImpl(CfgStateMachineDefineDao cfgStateMachineDefineDao) {
        super(cfgStateMachineDefineDao);
        this.cfgStateMachineDefineDao = cfgStateMachineDefineDao;
    }
    @Override
	public void saveMainBody(CfgStateMachineDefine cfgStateMachineDefine) {
		this.cfgStateMachineDefineDao.saveMainBody(cfgStateMachineDefine);
	}
}