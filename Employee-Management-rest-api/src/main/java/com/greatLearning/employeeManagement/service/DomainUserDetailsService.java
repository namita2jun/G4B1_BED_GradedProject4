package com.greatLearning.employeeManagement.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.greatLearning.employeeManagement.model.DomainUserDetails;
import com.greatLearning.employeeManagement.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

	private final UserJpaRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(email).map(DomainUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("invalid email passed"));

	}

}
