package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgFixedPropertyDefine;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgFixedPropertyDefineManager extends MgGenericManager<CfgFixedPropertyDefine, Long> {
    public void saveMainBody(CfgFixedPropertyDefine a0);
}