package com.btxy.basis.dao.st;


import org.mongodb.morphia.dao.DAO;

import com.btxy.basis.model.StSystemNoticeInfo;


/**
 * An interface that provides a data management interface to the StSystemNoticeInfo table.
 */
public interface StSystemNoticeInfoDao extends DAO<StSystemNoticeInfo, Long> {

public void saveMainBody(StSystemNoticeInfo a0);
}