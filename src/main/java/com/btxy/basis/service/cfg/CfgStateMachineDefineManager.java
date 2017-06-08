package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgStateMachineDefine;


import com.btxy.basis.service.base.MgGenericManager;


public interface CfgStateMachineDefineManager extends MgGenericManager<CfgStateMachineDefine, Long> {
    public void saveMainBody(CfgStateMachineDefine a0);
}