package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgFixedPropertyValueDao;
import com.btxy.basis.model.CfgFixedPropertyValue;
import com.btxy.basis.service.cfg.CfgFixedPropertyValueManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgFixedPropertyValueManager")
public class CfgFixedPropertyValueManagerImpl extends MgGenericManagerImpl<CfgFixedPropertyValue, Long> implements CfgFixedPropertyValueManager {
    CfgFixedPropertyValueDao cfgFixedPropertyValueDao;

    @Autowired
    public CfgFixedPropertyValueManagerImpl(CfgFixedPropertyValueDao cfgFixedPropertyValueDao) {
        super(cfgFixedPropertyValueDao);
        this.cfgFixedPropertyValueDao = cfgFixedPropertyValueDao;
    }
    @Override
	public void saveMainBody(CfgFixedPropertyValue cfgFixedPropertyValue) {
		this.cfgFixedPropertyValueDao.saveMainBody(cfgFixedPropertyValue);
	}
}