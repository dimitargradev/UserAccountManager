package com.westernacher.task.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncodeUtil {
	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);

	public static String encodePassword(String password) {
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		System.out.println(encoder.encode("admin"));
	}
}
