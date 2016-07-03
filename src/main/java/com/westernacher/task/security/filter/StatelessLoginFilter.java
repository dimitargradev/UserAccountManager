package com.westernacher.task.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.westernacher.task.model.User;
import com.westernacher.task.security.TokenAuthService;
import com.westernacher.task.security.UserAuthentication;
import com.westernacher.task.util.JsonUtil;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	TokenAuthService tokenAuthService;

	public StatelessLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authManager,
			TokenAuthService tokenAuthService) {
		super(defaultFilterProcessesUrl);
		setAuthenticationManager(authManager);
		this.tokenAuthService = tokenAuthService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		final User user = JsonUtil.MAPPER.readValue(request.getInputStream(), User.class);
		final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		final User authenticatedUser = (User) authentication.getPrincipal();
		final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

		tokenAuthService.addAuthentication(response, userAuthentication);

		response.getWriter().write("{}");
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}
}
