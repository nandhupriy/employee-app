package com.nandhu.employee.controller;

import com.nandhu.employee.model.Employee;
import com.nandhu.employee.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// Add Employee
	@PostMapping
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}

	// Get Employee by ID
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}

	// Update Employee
	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return employeeService.updateEmployee(id, employee);
	}

	// Delete Employee
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
	}

	// Get All Employees (with Pagination & Sorting)
	@GetMapping
	public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return employeeService.getAllEmployees(page, size, sortBy);
	}

	// Increment Salary by 10% for all employees
	@PutMapping("/increment-salary")
	public void incrementSalary() {
		employeeService.incrementSalaryByTenPercent();
	}
}
