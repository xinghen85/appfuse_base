package com.btxy.basis.service.st;

import java.util.List;

import com.btxy.basis.model.AuthUser;
import com.btxy.basis.model.StSystemNoticeInfo;
import com.btxy.basis.service.base.MgGenericManager;


public interface StSystemNoticeInfoManager extends MgGenericManager<StSystemNoticeInfo, Long> {
    public void saveMainBody(StSystemNoticeInfo a0);

	public List<StSystemNoticeInfo> getNoticeByUser(AuthUser user, Long library);
}