package com.westernacher.task.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.westernacher.task.model.User;
import com.westernacher.task.service.UserService;

@RestController
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public User register(@RequestBody @Valid User user) {
		return userService.save(user);
	}
}
