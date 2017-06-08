package com.btxy.basis.service.cfg;

import com.btxy.basis.dao.cfg.CfgFormInfoDao;
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.service.cfg.CfgFormInfoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("cfgFormInfoManager")
public class CfgFormInfoManagerImpl extends MgGenericManagerImpl<CfgFormInfo, Long> implements CfgFormInfoManager {
    CfgFormInfoDao cfgFormInfoDao;

    @Autowired
    public CfgFormInfoManagerImpl(CfgFormInfoDao cfgFormInfoDao) {
        super(cfgFormInfoDao);
        this.cfgFormInfoDao = cfgFormInfoDao;
    }
    @Override
	public void saveMainBody(CfgFormInfo cfgFormInfo) {
		this.cfgFormInfoDao.saveMainBody(cfgFormInfo);
	}
}