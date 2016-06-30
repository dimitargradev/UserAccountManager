package com.westernacher.task.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.westernacher.task.model.User;

public class UserAuthentication implements Authentication {

	private static final long serialVersionUID = 1L;

	private final User user;

	private boolean isAuthenticated = true;

	public UserAuthentication(User user) {
		this.user = user;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return user.getName();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public User getDetails() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

}
