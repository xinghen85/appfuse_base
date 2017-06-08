

package com.btxy.basis.dao.cfg;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.AuthShortCutPrivilege;

@Repository("authShortCutPrivilegeDao")
public class AuthShortCutPrivilegeDaoHibernate extends BasicDAO<AuthShortCutPrivilege, Long> implements AuthShortCutPrivilegeDao {

    public AuthShortCutPrivilegeDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(AuthShortCutPrivilege authShortCutPrivilege)  {
		super.save(authShortCutPrivilege);
	}    
}
