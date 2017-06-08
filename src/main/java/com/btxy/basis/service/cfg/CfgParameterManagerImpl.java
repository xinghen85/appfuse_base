package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgParameterDao;
import com.btxy.basis.model.CfgParameter;
import com.btxy.basis.service.cfg.CfgParameterManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgParameterManager")
public class CfgParameterManagerImpl extends MgGenericManagerImpl<CfgParameter, Long> implements CfgParameterManager {
    CfgParameterDao cfgParameterDao;

    @Autowired
    public CfgParameterManagerImpl(CfgParameterDao cfgParameterDao) {
        super(cfgParameterDao);
        this.cfgParameterDao = cfgParameterDao;
    }
    @Override
	public void saveMainBody(CfgParameter cfgParameter) {
		this.cfgParameterDao.saveMainBody(cfgParameter);
	}
}