package com.westernacher.task.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.westernacher.task.model.User;

public class SecurityUtil {

	public static User getCurrentUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if ((authentication == null) || (authentication.isAuthenticated() == false)
				|| (authentication instanceof AnonymousAuthenticationToken)) {
			return null;
		}

		return (User) authentication.getDetails();
	}
}
