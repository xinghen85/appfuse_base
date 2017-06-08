package com.btxy.basis.service.st;

import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btxy.basis.dao.st.AuthOrgUserDao;
import com.btxy.basis.model.AuthOrgUser;
import com.btxy.basis.service.base.MgGenericManagerImpl;


@Service("authOrgUserManager")
public class AuthOrgUserManagerImpl extends MgGenericManagerImpl<AuthOrgUser, Long> implements AuthOrgUserManager {
    AuthOrgUserDao authOrgUserDao;

    @Autowired
    public AuthOrgUserManagerImpl(AuthOrgUserDao authOrgUserDao) {
        super(authOrgUserDao);
        this.authOrgUserDao = authOrgUserDao;
    }
    @Override
	public void saveMainBody(AuthOrgUser authOrgUser) {
		this.authOrgUserDao.saveMainBody(authOrgUser);
	}
    @Override
	public Boolean checkUniqueIndexForUserName(Long userId,String userName) {
		if(userName==null || "".equals(userName.trim())){
			return true;
		}else{
			Query<AuthOrgUser> q=this.dao.createQuery();
			q.and(q.criteria("userName").equal(userName),q.criteria("_id").notEqual(userId));
			int cc=q.asKeyList().size();//this.dao.findIds("userName", userName).size();
			return cc<=0;
		}
		
	}


	@Override
	public Boolean checkUniqueIndexForEmail(Long userId,String email) {
		if(email==null || "".equals(email.trim())){
			return true;
		}else{
			Query<AuthOrgUser> q=this.dao.createQuery();
			q.and(q.criteria("email").equal(email),q.criteria("_id").notEqual(userId));
			int cc=q.asKeyList().size();
			return cc<=0;
		}
	}


	@Override
	public Boolean checkUniqueIndexForPhoneNumber(Long userId,String phoneNumber) {
		if(phoneNumber==null || "".equals(phoneNumber.trim())){
			return true;
		}else{
			Query<AuthOrgUser> q=this.dao.createQuery();
			q.and(q.criteria("phoneNumber").equal(phoneNumber),q.criteria("_id").notEqual(userId));
			int cc=q.asKeyList().size();
			return cc<=0;
		}
	}
	public AuthOrgUser getAuthOrgUserByObjectId(Long objectInfoId,Long objectId){
		Query<AuthOrgUser> q=this.dao.createQuery();
		q.criteria("objectId").equal(objectInfoId);
		q.criteria("objectInstanceId").equal(objectId);
		List<AuthOrgUser> list=q.asList();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}