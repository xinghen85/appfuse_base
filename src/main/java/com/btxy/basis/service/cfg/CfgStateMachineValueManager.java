package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgStateMachineValue;
import com.btxy.basis.service.base.MgGenericManager;

public interface CfgStateMachineValueManager extends MgGenericManager<CfgStateMachineValue, Long> {
    public void saveMainBody(CfgStateMachineValue a0);
}