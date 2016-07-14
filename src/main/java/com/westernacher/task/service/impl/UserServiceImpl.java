package com.westernacher.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.westernacher.task.model.EditUser;
import com.westernacher.task.model.User;
import com.westernacher.task.model.wrapper.PageResultWrapper;
import com.westernacher.task.repository.UserRepository;
import com.westernacher.task.service.UserService;
import com.westernacher.task.util.PasswordEncodeUtil;

@Service
public class UserServiceImpl implements UserService {

	private static Integer PER_PAGE = 10;

	@Autowired
	UserRepository userRepository;

	@Override
	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public PageResultWrapper<User> findAll(String orderBy, String direction, Integer page) {
		return new PageResultWrapper<>(userRepository.findAll(getPage(orderBy, direction, page)));
	}

	@Override
	public User save(User user) {
		final String password = user.getPassword();
		user.setPassword(PasswordEncodeUtil.encodePassword(password));
		return userRepository.save(user);
	}

	@Override
	public void delete(Integer id) {
		userRepository.delete(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User update(EditUser user) {
		final User dbUser = userRepository.findOne(user.getUserId());
		dbUser.setName(user.getName());
		dbUser.setEmail(user.getEmail());
		dbUser.setEnabled(user.isEnabled());
		return userRepository.save(dbUser);
	}

	private Pageable getPage(String orderBy, String orderDirection, Integer page) {
		if ((orderBy != null) && (orderDirection != null) && (page != null)) {
			final Direction direction = ("DESC").equals(orderDirection) ? Direction.DESC : Direction.ASC;
			return new PageRequest(page, PER_PAGE, direction, orderBy);
		} else if (page != null) {
			return new PageRequest(page, PER_PAGE);
		} else {
			return new PageRequest(0, PER_PAGE);
		}
	}
}
