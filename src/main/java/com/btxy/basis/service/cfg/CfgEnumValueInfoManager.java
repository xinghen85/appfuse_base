package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgEnumValueInfo;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgEnumValueInfoManager extends MgGenericManager<CfgEnumValueInfo, Long> {
    public void saveMainBody(CfgEnumValueInfo a0);
}