package com.btxy.basis.service.st;

import com.btxy.basis.model.LibraryInfo;


import com.btxy.basis.service.base.MgGenericManager;


public interface LibraryInfoManager extends MgGenericManager<LibraryInfo, Long> {
    public void saveMainBody(LibraryInfo a0);
}