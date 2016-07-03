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

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name="user")
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="creation_time")
	private Date creationTime;
	
	@PrePersist
	public void onInsert(){
		this.creationTime = new Date();
	}
	
	@Column(name="account_expired")
	@JsonIgnore
	private boolean isAccountExpired = false;
	
	@Column(name="account_locked")
	@JsonIgnore
	private boolean isAccountLocked = false;
	
	@Column(name="credentials_expired")
	@JsonIgnore
	private boolean isCredentialsExpired = false;
	
	@Column(name="is_enabled")
	@JsonIgnore
	private boolean isEnabled = true;
	
	@Transient
	@JsonIgnore
	private String token;
	
	@Transient
	@JsonIgnore
	public Date tokenCreationTime;

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !isAccountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !isAccountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired;
	}
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}
