package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgIconInfoDao;
import com.btxy.basis.model.CfgIconInfo;
import com.btxy.basis.service.cfg.CfgIconInfoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgIconInfoManager")
public class CfgIconInfoManagerImpl extends MgGenericManagerImpl<CfgIconInfo, Long> implements CfgIconInfoManager {
    CfgIconInfoDao cfgIconInfoDao;

    @Autowired
    public CfgIconInfoManagerImpl(CfgIconInfoDao cfgIconInfoDao) {
        super(cfgIconInfoDao);
        this.cfgIconInfoDao = cfgIconInfoDao;
    }
    @Override
	public void saveMainBody(CfgIconInfo cfgIconInfo) {
		this.cfgIconInfoDao.saveMainBody(cfgIconInfo);
	}
}