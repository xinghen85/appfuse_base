package com.btxy.basis.service.st;

import com.btxy.basis.dao.st.LibraryInfoDao;
import com.btxy.basis.model.LibraryInfo;
import com.btxy.basis.service.st.LibraryInfoManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("libraryInfoManager")
public class LibraryInfoManagerImpl extends MgGenericManagerImpl<LibraryInfo, Long> implements LibraryInfoManager {
    LibraryInfoDao libraryInfoDao;

    @Autowired
    public LibraryInfoManagerImpl(LibraryInfoDao libraryInfoDao) {
        super(libraryInfoDao);
        this.libraryInfoDao = libraryInfoDao;
    }
    @Override
	public void saveMainBody(LibraryInfo libraryInfo) {
		this.libraryInfoDao.saveMainBody(libraryInfo);
	}
}