package com.btxy.basis.dao.st;

import java.util.List;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.util.HTTP;
import com.btxy.basis.util.ValidateUtil;

@Repository("authAppUserDao")
public class AuthAppUserDaoHibernate extends BasicDAO<AuthAppUser, Long> implements AuthAppUserDao, UserDetailsService {
	private static Logger log = Logger.getLogger(HTTP.class);
	@Autowired
	
    public AuthAppUserDaoHibernate(@Qualifier("datastore")final Datastore ds) {
		super(ds);
    }

	@Override
	public void saveMainBody(AuthAppUser authAppUser) {
		AuthAppUser authAppUser2 = this.get(authAppUser.getUserId());
		if (authAppUser != null && authAppUser2 != null) {
			authAppUser.setLibraryRoleList(authAppUser2.getLibraryRoleList());
		}
		this.save(authAppUser);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Query<AuthAppUser> qry = this.createQuery();
		if (ValidateUtil.checkEmail(username)) {
			qry.and(qry.criteria("email").equal(username));
		} else if (ValidateUtil.checkPhone(username)) {
			qry.and(qry.criteria("phoneNumber").equal(new Long(username)));
		} else {
			qry.and(qry.criteria("userName").equal(username));
		}

		List<AuthAppUser> users = qry.asList();

		if (users.isEmpty()) {
			throw new UsernameNotFoundException("user '" + username + "' not found...");
		} else {
			log.info("loadUserByUsername[" + username + "]:" + users.get(0).toString());
			return (UserDetails) users.get(0);
		}
	}

}
