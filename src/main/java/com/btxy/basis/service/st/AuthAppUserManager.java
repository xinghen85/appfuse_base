package com.btxy.basis.service.st;

import java.util.List;

import com.btxy.basis.model.AuthAppUser;
import com.btxy.basis.model.AuthUserLibraryRole;
import com.btxy.basis.service.base.MgGenericManager;


public interface AuthAppUserManager extends MgGenericManager<AuthAppUser, Long> {
    public void saveMainBody(AuthAppUser a0);

	public Boolean checkUniqueIndexForUserName(Long userId, String userName);

	public Boolean checkUniqueIndexForEmail(Long userId,String email);

	public Boolean checkUniqueIndexForPhoneNumber(Long userId,String phoneNumber);
	
	public AuthAppUser saveForChangePassword(AuthAppUser user);
	
	public String getEncoderedPasword(String password);
	
	public AuthAppUser libraryRoleSave(AuthAppUser user);

	public void saveAuthUserLibraryRole(AuthAppUser authAppUser,
			List<AuthUserLibraryRole> list);
}