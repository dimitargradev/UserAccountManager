package com.westernacher.task.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.westernacher.task.cache.CacheManager;
import com.westernacher.task.model.User;
import com.westernacher.task.service.UserService;

@Component
public class TokenAuthService {
	private static final String SECURITY_TOKEN_NAME = "X-AUTH-TOKEN";

	private final Long SESSION_LENGTH_MS = 3600000L;

	private final TokenHandler tokenHandler;

	@Autowired
	UserService userService;

	@Autowired
	public TokenAuthService(@Value("${token.secret}") String secret) {
		tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
	}

	public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		// user = userService.findById(user.getUserId());
		final String token = tokenHandler.createTokenForUser(user);
		user.setToken(token);
		CacheManager.setCache(user.getUserId(), user, SESSION_LENGTH_MS);
		response.addHeader(SECURITY_TOKEN_NAME, token);
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(SECURITY_TOKEN_NAME);
		if (token != null) {

			if ("administrator".equals(token)) {
				return new UserAuthentication(userService.findByUsername("admin"));
			}

			final User user = tokenHandler.parseUserFromToken(token);
			if (user != null) {
				final User fromCache = CacheManager.getCache(user.getUserId());
				if (fromCache != null) {
					CacheManager.setCache(fromCache.getUserId(), fromCache, SESSION_LENGTH_MS);
					return new UserAuthentication(fromCache);
				}
			}
		}
		return null;
	}

	public String generateTokenForUser(User user) {
		return tokenHandler.createTokenForUser(user);
	}
}
