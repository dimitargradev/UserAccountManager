package com.westernacher.task.service;

import com.westernacher.task.model.User;

public interface UserService extends BaseService<User, Integer> {

	public User update(User user);

	public User findByUsername(String username);
}
