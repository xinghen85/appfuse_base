package com.btxy.basis.service.st;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.btxy.basis.dao.st.AuthAppUserDao;
import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.AuthUserLibraryRole;
import com.btxy.basis.service.base.MgGenericManagerImpl;
import com.btxy.basis.util.pass.AES;

@Service("authAppUserManager")
public class AuthAppUserManagerImpl extends MgGenericManagerImpl<AuthAppUser, Long> implements AuthAppUserManager {
	AuthAppUserDao authAppUserDao;

	@Autowired
	public AuthAppUserManagerImpl(AuthAppUserDao authAppUserDao) {
		super(authAppUserDao);
		this.authAppUserDao = authAppUserDao;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void saveMainBody(AuthAppUser authAppUser) {
		this.authAppUserDao.saveMainBody(authAppUser);
	}

	public AuthAppUser save(AuthAppUser object) {
		return save(object, false);
	}

	public String getEncoderedPasword(String password) {
		if (passwordEncoder != null) {
			return passwordEncoder.encodePassword(password, null);
		} else {
			return null;
		}
	}

	public AuthAppUser saveForChangePassword(AuthAppUser user) {
		if (passwordEncoder != null) {
			// Check whether we have to encrypt (or re-encrypt) the password

			try {
				user.setPasswordHint(new AES().encrypt(user.getPassword()));
			} catch (Exception e) {
				e.printStackTrace();
			}

			user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));

		} else {
			log.warn("PasswordEncoder not set, skipping password encryption...");
		}
		dao.save(user);
		return user;
	}

	public AuthAppUser save(AuthAppUser user, boolean ifNew) {
		if (passwordEncoder != null) {
			// Check whether we have to encrypt (or re-encrypt) the password
			if (ifNew) {
				// New user, always encrypt
				try {
					user.setPasswordHint(new AES().encrypt(user.getPassword()));
				} catch (Exception e) {
					e.printStackTrace();
				}

				user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));

				user.setCreateTime(new Date());
				user.setUpdateTime(new Date());

			} else {

				AuthAppUser olduser = this.get(user.getUserId());
				if (olduser != null) {
					user.setPassword(olduser.getPassword());
					user.setPasswordHint(olduser.getPasswordHint());
					user.setLibraryRoleList(olduser.getLibraryRoleList());
				}
				user.setCreateTime(olduser.getCreateTime());
				user.setUpdateTime(new Date());
			}

		} else {
			log.warn("PasswordEncoder not set, skipping password encryption...");
		}
		dao.save(user);
		return user;
	}

	public AuthAppUser libraryRoleSave(AuthAppUser user) {

		AuthAppUser olduser = this.get(user.getUserId());
		if (olduser != null) {
			user.setPassword(olduser.getPassword());
			user.setPasswordHint(olduser.getPasswordHint());
			// user.setLibraryRoleList(olduser.getLibraryRoleList());
		}
		// user.setCreateTime(olduser.getCreateTime());
		user.setUpdateTime(new Date());

		dao.save(user);
		return user;
	}

	@Override
	public Boolean checkUniqueIndexForUserName(Long userId, String userName) {
		if (userName == null || "".equals(userName.trim())) {
			return true;
		} else {
			Query<AuthAppUser> q = this.dao.createQuery();
			q.and(q.criteria("userName").equal(userName), q.criteria("_id").notEqual(userId));
			int cc = q.asKeyList().size();// this.dao.findIds("userName",
											// userName).size();
			return cc <= 0;
		}

	}

	@Override
	public Boolean checkUniqueIndexForEmail(Long userId, String email) {
		if (email == null || "".equals(email.trim())) {
			return true;
		} else {
			// int cc=this.dao.findIds("email", email).size();
			Query<AuthAppUser> q = this.dao.createQuery();
			q.and(q.criteria("email").equal(email), q.criteria("_id").notEqual(userId));
			int cc = q.asKeyList().size();
			return cc <= 0;
		}
	}

	@Override
	public Boolean checkUniqueIndexForPhoneNumber(Long userId, String phoneNumber) {
		if (phoneNumber == null || "".equals(phoneNumber.trim())) {
			return true;
		} else {
			// int cc=this.dao.findIds("phoneNumber", new
			// Long(phoneNumber)).size();
			Query<AuthAppUser> q = this.dao.createQuery();
			q.and(q.criteria("phoneNumber").equal(phoneNumber), q.criteria("_id").notEqual(userId));
			int cc = q.asKeyList().size();
			return cc <= 0;
		}
	}

	@Override
	public void saveAuthUserLibraryRole(AuthAppUser authAppUser, List<AuthUserLibraryRole> list) {
		AuthAppUser olduser = this.get(authAppUser.getUserId());
		/*
		 * if(olduser!=null && olduser.getPasswordHint()!=null &&
		 * olduser.getPasswordHint().equals(user.getPasswordHint())){
		 */
		if (olduser != null) {

			olduser.setLibraryRoleList(list);
		}
		dao.save(olduser);
	}

}