package com.btxy.basis.service.cfg;

import com.btxy.basis.model.CfgEnumInfo;

import java.util.List;

import javax.jws.WebService;

import com.btxy.basis.service.base.MgGenericManager;


public interface CfgEnumInfoManager extends MgGenericManager<CfgEnumInfo, Long> {
    public void saveMainBody(CfgEnumInfo a0);
    
    public List<CfgEnumInfo> getCfgEnumInfoByCode(String enumCode);
}