package com.btxy.basis.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.btxy.basis.model.AuthUser;

public class CurrentUserUtil {
	public static UserDetails getCurrentUserDetail() {
		return (UserDetails) getCurrentUser();
	}

	public static AuthUser getCurrentUser() {
		return getCurrentUser(SecurityContextHolder.getContext().getAuthentication());
	}

	private static AuthUser getCurrentUser(Authentication auth) {
		AuthUser currentUser = null;
		if (auth == null) {
			currentUser = null;
		} else if (auth.getPrincipal() instanceof UserDetails) {
			currentUser = (AuthUser) auth.getPrincipal();
		} else if (auth.getDetails() instanceof UserDetails) {
			currentUser = (AuthUser) auth.getDetails();
		} else {
			return null;
		}
		return currentUser;
	}

}
