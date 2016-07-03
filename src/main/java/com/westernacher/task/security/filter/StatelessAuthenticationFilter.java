package com.westernacher.task.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.westernacher.task.security.TokenAuthService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthService tokenAuthService;

	public StatelessAuthenticationFilter(TokenAuthService tokenAuthService) {
		this.tokenAuthService = tokenAuthService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final Authentication authentication = tokenAuthService.getAuthentication((HttpServletRequest) request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}
}
