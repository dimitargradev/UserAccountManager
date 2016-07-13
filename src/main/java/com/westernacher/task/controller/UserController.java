package com.westernacher.task.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.westernacher.task.model.EditUser;
import com.westernacher.task.model.User;
import com.westernacher.task.service.UserService;
import com.westernacher.task.util.SecurityUtil;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(path = "/current", method = RequestMethod.GET)
	public User getCurrentUser() {
		return SecurityUtil.getCurrentUser();
	}

	@ResponseBody
	@RequestMapping(path = "/all", method = RequestMethod.GET)
	public List<User> getAllUsers(@RequestParam(value = "orderBy", required = false) String orderBy,
			@RequestParam(value = "direction", required = false) String direction) {
		return userService.findAll(orderBy, direction);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public User createUser(@RequestBody @Valid User user) {
		return userService.save(user);
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public User updateUser(@RequestBody @Valid EditUser user) {
		return userService.update(user);
	}

	@ResponseBody
	@RequestMapping(path = "/delete/{userId}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable Integer userId) {
		userService.delete(userId);
	}
}
