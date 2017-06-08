

package com.btxy.basis.dao.st;

import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.stereotype.Repository;

import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.AuthAppRole;

@Repository("authAppRoleDao")
public class AuthAppRoleDaoHibernate extends BasicDAO<AuthAppRole, Long> implements AuthAppRoleDao {

    public AuthAppRoleDaoHibernate() {
         super(SpringContext.getDatastore());
    }
	@Override
	public void saveMainBody(AuthAppRole authAppRole)  {
		AuthAppRole authAppRole2=super.get(authAppRole.getRoleId());
		super.save(authAppRole);
	}    
}
