package com.westernacher.task.service;

import com.westernacher.task.model.EditUser;
import com.westernacher.task.model.User;

public interface UserService extends BaseService<User, Integer> {

	public User update(EditUser user);

	public User findByUsername(String username);
}
