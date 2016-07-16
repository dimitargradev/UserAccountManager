package com.westernacher.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.westernacher.task.model.User;
import com.westernacher.task.repository.UserRepository;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	UserRepository userRepository;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public User getUser() {
		throw new RuntimeException("Testing the exceptions");
		// return userRepository.findByUsername("admin");
	}
}
