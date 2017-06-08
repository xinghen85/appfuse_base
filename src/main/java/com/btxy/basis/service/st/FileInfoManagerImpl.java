package com.btxy.basis.service.st;

import com.btxy.basis.dao.st.FileInfoDao;
import com.btxy.basis.model.FileInfo;
import com.btxy.basis.service.st.FileInfoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("fileInfoManager")
public class FileInfoManagerImpl extends MgGenericManagerImpl<FileInfo, Long> implements FileInfoManager {
    FileInfoDao fileInfoDao;

    @Autowired
    public FileInfoManagerImpl(FileInfoDao fileInfoDao) {
        super(fileInfoDao);
        this.fileInfoDao = fileInfoDao;
    }
    @Override
	public void saveMainBody(FileInfo fileInfo) {
		this.fileInfoDao.saveMainBody(fileInfo);
	}
}