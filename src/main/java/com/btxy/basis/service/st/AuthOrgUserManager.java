package com.btxy.basis.service.st;

import com.btxy.basis.model.AuthOrgUser;


import com.btxy.basis.service.base.MgGenericManager;


public interface AuthOrgUserManager extends MgGenericManager<AuthOrgUser, Long> {
    public void saveMainBody(AuthOrgUser a0);
    
    public Boolean checkUniqueIndexForUserName(Long userId, String userName);

	public Boolean checkUniqueIndexForEmail(Long userId,String email);

	public Boolean checkUniqueIndexForPhoneNumber(Long userId,String phoneNumber);
	
	public AuthOrgUser getAuthOrgUserByObjectId(Long objectInfoId,Long objectId);
}