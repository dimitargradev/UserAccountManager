package com.westernacher.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.westernacher.task.security.TokenAuthService;
import com.westernacher.task.security.filter.StatelessAuthenticationFilter;
import com.westernacher.task.security.filter.StatelessLoginFilter;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	TokenAuthService tokenAuthService;

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			// For debugging reasons
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/users/unique").anonymous()
			.antMatchers(HttpMethod.POST, "/login").anonymous()
			.antMatchers(HttpMethod.POST, "/register").anonymous()
			.anyRequest()
			.authenticated()
			.and()
			.addFilterBefore(new StatelessLoginFilter("/login", authenticationManagerBean(), tokenAuthService),
					UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthService),
					UsernamePasswordAuthenticationFilter.class);
		// @formatter:on
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
}
