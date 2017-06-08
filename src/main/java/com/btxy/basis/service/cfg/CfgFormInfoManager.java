package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgFormInfo;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgFormInfoManager extends MgGenericManager<CfgFormInfo, Long> {
    public void saveMainBody(CfgFormInfo a0);
}