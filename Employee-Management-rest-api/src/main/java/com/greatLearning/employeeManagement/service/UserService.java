package com.greatLearning.employeeManagement.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.greatLearning.employeeManagement.model.User;
import com.greatLearning.employeeManagement.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserJpaRepository userRepository;

	public Set<User> listUsers() {
		return Set.copyOf(this.userRepository.findAll());

	}

	public User getUserById(Long id) {
		return this.userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user id passed"));
	}

	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
}
