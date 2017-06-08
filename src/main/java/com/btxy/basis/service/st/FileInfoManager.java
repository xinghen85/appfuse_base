package com.btxy.basis.service.st;

import com.btxy.basis.model.FileInfo;


import com.btxy.basis.service.base.MgGenericManager;


public interface FileInfoManager extends MgGenericManager<FileInfo, Long> {
    public void saveMainBody(FileInfo a0);
}