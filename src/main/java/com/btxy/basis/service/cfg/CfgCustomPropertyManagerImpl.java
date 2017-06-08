package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgCustomPropertyDao;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.service.cfg.CfgCustomPropertyManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgCustomPropertyManager")
public class CfgCustomPropertyManagerImpl extends MgGenericManagerImpl<CfgCustomProperty, Long> implements CfgCustomPropertyManager {
    CfgCustomPropertyDao cfgCustomPropertyDao;

    @Autowired
    public CfgCustomPropertyManagerImpl(CfgCustomPropertyDao cfgCustomPropertyDao) {
        super(cfgCustomPropertyDao);
        this.cfgCustomPropertyDao = cfgCustomPropertyDao;
    }
    @Override
	public void saveMainBody(CfgCustomProperty cfgCustomProperty) {
		this.cfgCustomPropertyDao.saveMainBody(cfgCustomProperty);
	}
}