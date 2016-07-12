package com.westernacher.task.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "username")
	@Length(min = 5)
	@NotEmpty
	private String username;

	@Column(name = "password")
	@Length(min = 5)
	@NotEmpty
	private String password;

	@Column(name = "name")
	@Length(min = 5)
	@NotEmpty
	private String name;

	@Column(name = "email")
	@Email
	@NotEmpty
	private String email;

	@Column(name = "creation_time")
	private Date creationTime;

	@Column(name = "account_expired")
	@JsonIgnore
	private boolean isAccountExpired = false;

	@Column(name = "account_locked")
	@JsonIgnore
	private boolean isAccountLocked = false;

	@Column(name = "credentials_expired")
	@JsonIgnore
	private boolean isCredentialsExpired = false;

	@Column(name = "is_enabled")
	@JsonIgnore
	private boolean isEnabled = true;

	@Transient
	@JsonIgnore
	private String token;

	@Transient
	@JsonIgnore
	public Date tokenCreationTime;

	@PrePersist
	public void onInsert() {
		this.creationTime = new Date();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenCreationTime() {
		return tokenCreationTime;
	}

	public void setTokenCreationTime(Date tokenCreationTime) {
		this.tokenCreationTime = tokenCreationTime;
	}

	public boolean isAccountExpired() {
		return isAccountExpired;
	}

	public boolean isAccountLocked() {
		return isAccountLocked;
	}

	public boolean isCredentialsExpired() {
		return isCredentialsExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !this.isAccountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !this.isAccountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !this.isCredentialsExpired;
	}

	public void setAccountExpired(boolean isAccountExpired) {
		this.isAccountExpired = isAccountExpired;
	}

	public void setAccountLocked(boolean isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}

	public void setCredentialsExpired(boolean isCredentialsExpired) {
		this.isCredentialsExpired = isCredentialsExpired;
	}
}
