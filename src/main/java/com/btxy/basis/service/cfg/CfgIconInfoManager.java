package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgIconInfo;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgIconInfoManager extends MgGenericManager<CfgIconInfo, Long> {
    public void saveMainBody(CfgIconInfo a0);
}