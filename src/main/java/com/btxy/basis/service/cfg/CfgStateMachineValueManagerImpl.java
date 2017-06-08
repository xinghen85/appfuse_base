package com.btxy.basis.service.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btxy.basis.dao.cfg.CfgStateMachineValueDao;
import com.btxy.basis.model.CfgStateMachineValue;
import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgStateMachineValueManager")
public class CfgStateMachineValueManagerImpl extends MgGenericManagerImpl<CfgStateMachineValue, Long> implements CfgStateMachineValueManager {
    CfgStateMachineValueDao cfgStateMachineValueDao;

    @Autowired
    public CfgStateMachineValueManagerImpl(CfgStateMachineValueDao cfgStateMachineValueDao) {
        super(cfgStateMachineValueDao);
        this.cfgStateMachineValueDao = cfgStateMachineValueDao;
    }
    @Override
	public void saveMainBody(CfgStateMachineValue cfgStateMachineValue) {
		this.cfgStateMachineValueDao.saveMainBody(cfgStateMachineValue);
	}
}