package com.westernacher.task.service;

import com.westernacher.task.model.EditUser;
import com.westernacher.task.model.User;
import com.westernacher.task.model.wrapper.PageResultWrapper;

public interface UserService extends BaseService<User, Integer> {

	public User update(EditUser user);

	public User findByUsername(String username);

	public PageResultWrapper<User> findAll(String orderBy, String direction, Integer page);
}
