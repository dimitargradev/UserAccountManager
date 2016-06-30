package com.westernacher.task.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.westernacher.task.model.User;
import com.westernacher.task.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User is not found");
		}
		detailsChecker.check(user);
		return user;
	}

}
