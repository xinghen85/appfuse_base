package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgFixedPropertyDefineDao;
import com.btxy.basis.model.CfgFixedPropertyDefine;
import com.btxy.basis.service.cfg.CfgFixedPropertyDefineManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgFixedPropertyDefineManager")
public class CfgFixedPropertyDefineManagerImpl extends MgGenericManagerImpl<CfgFixedPropertyDefine, Long> implements CfgFixedPropertyDefineManager {
    CfgFixedPropertyDefineDao cfgFixedPropertyDefineDao;

    @Autowired
    public CfgFixedPropertyDefineManagerImpl(CfgFixedPropertyDefineDao cfgFixedPropertyDefineDao) {
        super(cfgFixedPropertyDefineDao);
        this.cfgFixedPropertyDefineDao = cfgFixedPropertyDefineDao;
    }
    @Override
	public void saveMainBody(CfgFixedPropertyDefine cfgFixedPropertyDefine) {
		this.cfgFixedPropertyDefineDao.saveMainBody(cfgFixedPropertyDefine);
	}
}