package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgFixedPropertyValue;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgFixedPropertyValueManager extends MgGenericManager<CfgFixedPropertyValue, Long> {
    public void saveMainBody(CfgFixedPropertyValue a0);
}