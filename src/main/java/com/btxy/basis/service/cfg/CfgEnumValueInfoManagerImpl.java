package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgEnumValueInfoDao;
import com.btxy.basis.model.CfgEnumValueInfo;
import com.btxy.basis.service.cfg.CfgEnumValueInfoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgEnumValueInfoManager")
public class CfgEnumValueInfoManagerImpl extends MgGenericManagerImpl<CfgEnumValueInfo, Long> implements CfgEnumValueInfoManager {
    CfgEnumValueInfoDao cfgEnumValueInfoDao;

    @Autowired
    public CfgEnumValueInfoManagerImpl(CfgEnumValueInfoDao cfgEnumValueInfoDao) {
        super(cfgEnumValueInfoDao);
        this.cfgEnumValueInfoDao = cfgEnumValueInfoDao;
    }
    @Override
	public void saveMainBody(CfgEnumValueInfo cfgEnumValueInfo) {
		this.cfgEnumValueInfoDao.saveMainBody(cfgEnumValueInfo);
	}
}