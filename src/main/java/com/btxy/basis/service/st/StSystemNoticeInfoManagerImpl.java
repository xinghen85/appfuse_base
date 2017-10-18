package com.btxy.basis.service.st;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btxy.basis.dao.st.StSystemNoticeInfoDao;
import com.btxy.basis.model.AuthUser;
import com.btxy.basis.model.StSystemNoticeInfo;
import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("stSystemNoticeInfoManager")
public class StSystemNoticeInfoManagerImpl extends MgGenericManagerImpl<StSystemNoticeInfo, Long> implements StSystemNoticeInfoManager {
    StSystemNoticeInfoDao stSystemNoticeInfoDao;

    @Autowired
    public StSystemNoticeInfoManagerImpl(StSystemNoticeInfoDao stSystemNoticeInfoDao) {
        super(stSystemNoticeInfoDao);
        this.stSystemNoticeInfoDao = stSystemNoticeInfoDao;
    }
    @Override
	public void saveMainBody(StSystemNoticeInfo stSystemNoticeInfo) {
		this.stSystemNoticeInfoDao.saveMainBody(stSystemNoticeInfo);
	}
	@Override
	public List<StSystemNoticeInfo> getNoticeByUser(AuthUser user, Long library) {
		Query<StSystemNoticeInfo> q=dao.createQuery();
		List<Long> ids=new ArrayList<Long>();
		ids.add(user.getUserId());
		if(user.getRoleList(library)!=null && user.getRoleList(library).size()>0){
			q.and(q.criteria("status").equal("BVF"),q.or(
					q.criteria("overt").equal(true),q.and(
							q.criteria("library").equal(library),q.or(
									q.criteria("roleList").hasAnyOf(user.getRoleList(library)),q.criteria("userList").hasAnyOf(ids)
							)
					)
			));

		}else{
			q.and(q.criteria("status").equal("BVF"),q.or(
					q.criteria("overt").equal(true),q.and(
							q.criteria("library").equal(library),q.criteria("userList").hasAnyOf(ids)
					)
			));

		}
		//q.and(q.criteria("status").equal("BVF"),q.or(q.criteria("userList").hasThisOne(user.getUserId())));
		q.order("-"+Mapper.ID_KEY);
		
		return q.asList();
	}
}