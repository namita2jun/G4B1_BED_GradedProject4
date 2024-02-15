package com.greatLearning.employeeManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatLearning.employeeManagement.model.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String name);

}
