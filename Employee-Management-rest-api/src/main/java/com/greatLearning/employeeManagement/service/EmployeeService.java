package com.greatLearning.employeeManagement.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.greatLearning.employeeManagement.model.Employees;
import com.greatLearning.employeeManagement.repository.EmployeeJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeJpaRepository employeeRepository;

	public Map<String, Object> fetchOrders(int page, int size, String sort, String property) {
		Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = PageRequest.of(page, size, direction, property);
		Page<Employees> pageResponse = this.employeeRepository.findAll(pageRequest);

		int totalPages = pageResponse.getTotalPages();
		int noOfRecords = pageResponse.getNumberOfElements();
		List<Employees> content = pageResponse.getContent();

		Map<String, Object> responseMap = new LinkedHashMap<>();
		responseMap.put("pages", totalPages);
		responseMap.put("records", noOfRecords);
		responseMap.put("data", content);
		return responseMap;
	}

	public Employees fetchEmployeeById(Long id) {
		return this.employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee id passed"));

	}

	public Employees saveEmployee(Employees employee) {
		return this.employeeRepository.save(employee);

	}

	public Employees updateEmployeeById(Long id, Employees employee) {
		Employees existingEmployee = this.employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee id passed"));
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());

		return this.employeeRepository.save(existingEmployee);
	}

	public void deleteEmployee(Long id) {
		this.employeeRepository.deleteById(id);
	}
	
	public List<Employees> searchEmployees(String query){
		List<Employees> employees =  this.employeeRepository.searchEmployees(query);
		return employees;
	}
}
