package com.westernacher.task.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.westernacher.task.model.User;

public interface UserRepository extends JpaRepository<User, Serializable> {

	public User findByUsername(String username);

	public User findByEmail(String email);
}
