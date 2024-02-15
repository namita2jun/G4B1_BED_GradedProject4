package com.greatLearning.employeeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greatLearning.employeeManagement.model.Employees;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employees, Long> {
	
	@Query("SELECT e FROM Employees e WHERE " +
		"e.firstName LIKE CONCAT('%',:query, '%')")
	List<Employees> searchEmployees(String query);

}