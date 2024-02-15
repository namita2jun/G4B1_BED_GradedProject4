package com.greatLearning.employeeManagement.controller;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greatLearning.employeeManagement.model.User;
import com.greatLearning.employeeManagement.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping
	public Set<User> fetchUsers() {
		return this.userService.listUsers();
	}

	@GetMapping("/{id}")
	public User fetchUserById(@PathVariable("id") long id) {
		return this.userService.getUserById(id);
	}

	@PostMapping
	public User saveUser(@RequestBody User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		return this.userService.saveUser(user);
	}

}
