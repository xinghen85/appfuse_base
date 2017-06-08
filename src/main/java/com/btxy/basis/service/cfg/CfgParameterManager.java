package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgParameter;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgParameterManager extends MgGenericManager<CfgParameter, Long> {
    public void saveMainBody(CfgParameter a0);
}