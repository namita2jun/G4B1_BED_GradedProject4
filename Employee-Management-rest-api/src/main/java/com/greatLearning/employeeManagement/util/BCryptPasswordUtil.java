package com.greatLearning.employeeManagement.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordUtil {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String password = "welcome";

		String cipherText = passwordEncoder.encode(password);

		System.out.println(cipherText);

		boolean matches = passwordEncoder.matches(password + ",", cipherText);

		System.out.println(matches);
	}

}
