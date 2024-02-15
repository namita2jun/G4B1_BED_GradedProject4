package com.greatLearning.employeeManagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.greatLearning.employeeManagement.model.Employees;
import com.greatLearning.employeeManagement.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

	private final EmployeeService employeeService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Employees saveEmployees(@Valid @RequestBody Employees employees) {
		return this.employeeService.saveEmployee(employees);
	}

	@GetMapping
	public Map<String, Object> fetchEmployees(
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			@RequestParam(name = "order", defaultValue = "asc", required = false) String direction,
			@RequestParam(name = "field", defaultValue = "firstName", required = false) String property) {
		return this.employeeService.fetchOrders(page, size, direction, property);
	}

	@GetMapping("/{id}")
	public Employees fetchEmployeeById(@PathVariable("id") Long id) {
		return this.employeeService.fetchEmployeeById(id);
	}

	@GetMapping("/search")
	@ResponseStatus(code = HttpStatus.OK)
	public List<Employees> searchEmployees(@RequestParam("query") String query) {
		return this.employeeService.searchEmployees(query);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployees(@PathVariable("id") Long id) {
		this.employeeService.deleteEmployee(id);
		return new ResponseEntity<>("Deleted employee-" + id + "", HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employees> updatedEmployees(@PathVariable("id") Long id, @RequestBody Employees employees) {
		Employees UpdatedEmployee = this.employeeService.updateEmployeeById(id, employees);
		return new ResponseEntity<>(UpdatedEmployee, HttpStatus.OK);
	}
}
