

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.AuthPrivilegeInfo;

@Repository("authPrivilegeInfoDao")
public class AuthPrivilegeInfoDaoHibernate extends BasicDAO<AuthPrivilegeInfo, Long> implements AuthPrivilegeInfoDao {

    public AuthPrivilegeInfoDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(AuthPrivilegeInfo authPrivilegeInfo)  {
		super.save(authPrivilegeInfo);
	}    
}
