package com.nandhu.employee.service;

import com.nandhu.employee.model.Employee;
import com.nandhu.employee.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	// Save Employee
	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	// Get Employee By Id with caching
	@Override
	@Cacheable(value = "employees", key = "#id")
	public Employee getEmployeeById(Long id) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		return optionalEmployee.orElse(null);
	}

	// Get All Employees with caching
	@Override
	@Cacheable(value = "employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// Update Employee and evict cache
	@Override
	@CacheEvict(value = "employees", allEntries = true)
	public Employee updateEmployee(Long id, Employee employee) {
		employee.setId(id); // very important step
		return employeeRepository.save(employee);
	}

	// Delete Employee and evict cache
	@Override
	@CacheEvict(value = "employees", allEntries = true)
	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}

	// Pagination & Sorting (no cache needed)
	@Override
	public Page<Employee> getAllEmployees(int page, int size, String sortBy) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
		return employeeRepository.findAll(pageable);
	}

	// Salary increment using multi-threading (no cache)
	@Override
	public void incrementSalaryByTenPercent() {
		List<Employee> employees = employeeRepository.findAll();

		// Multi-threading salary increment
		employees.parallelStream().forEach(employee -> {
			double newSalary = employee.getSalary() * 1.10; // 10% increment
			employee.setSalary(newSalary);
		});

		// Save all employees at once (batch save)
		employeeRepository.saveAll(employees);
	}
}
