package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgCustomProperty;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgCustomPropertyManager extends MgGenericManager<CfgCustomProperty, Long> {
    public void saveMainBody(CfgCustomProperty a0);
}