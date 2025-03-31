package com.nandhu.employee.service;

import com.nandhu.employee.model.Employee;
import com.nandhu.employee.repository.EmployeeRepository;
import com.nandhu.employee.service.EmployeeServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	void testSaveEmployee() {
		Employee employee = new Employee("John", "IT", 5000.0);
		when(employeeRepository.save(employee)).thenReturn(employee);

		Employee savedEmployee = employeeService.saveEmployee(employee);

		assertNotNull(savedEmployee);
		assertEquals("John", savedEmployee.getName());
		verify(employeeRepository, times(1)).save(employee);
	}

	@Test
	void testGetEmployeeById() {
		Employee employee = new Employee("John", "IT", 5000.0);
		employee.setId(1L);
		when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

		Employee foundEmployee = employeeService.getEmployeeById(1L);

		assertNotNull(foundEmployee);
		assertEquals(1L, foundEmployee.getId());
	}

	@Test
	void testGetAllEmployees() {
		List<Employee> employees = Arrays.asList(new Employee("John", "IT", 5000.0),
				new Employee("Jane", "HR", 6000.0));
		when(employeeRepository.findAll()).thenReturn(employees);

		List<Employee> result = employeeService.getAllEmployees();

		assertEquals(2, result.size());
	}

	@Test
	void testUpdateEmployee() {
		// Given
		Long employeeId = 1L;
		Employee updatedEmployee = new Employee("John", "HR", 6000.0);
		updatedEmployee.setId(employeeId); // Ensuring ID is set

		when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// When
		Employee result = employeeService.updateEmployee(employeeId, updatedEmployee);

		// Then
		assertNotNull(result);
		assertEquals(employeeId, result.getId()); // ID should remain same
		assertEquals("HR", result.getDepartment()); // Updated dept
		assertEquals(6000.0, result.getSalary()); // Updated salary

		verify(employeeRepository).save(any(Employee.class));
	}

	@Test
	void testDeleteEmployee() {
		Long employeeId = 1L;

		employeeService.deleteEmployee(employeeId);

		verify(employeeRepository, times(1)).deleteById(employeeId);
	}

	@Test
	void testGetAllEmployeesWithPaginationAndSorting() {
		List<Employee> employees = Arrays.asList(new Employee("John", "IT", 5000.0),
				new Employee("Jane", "HR", 6000.0));
		Page<Employee> page = new PageImpl<>(employees);
		Pageable pageable = PageRequest.of(0, 2, Sort.by("name"));

		when(employeeRepository.findAll(pageable)).thenReturn(page);

		Page<Employee> result = employeeService.getAllEmployees(0, 2, "name");

		assertEquals(2, result.getContent().size());
		verify(employeeRepository, times(1)).findAll(pageable);
	}

	@Test
	void testIncrementSalaryByTenPercent() {
		List<Employee> employees = Arrays.asList(new Employee("John", "IT", 5000.0),
				new Employee("Jane", "HR", 6000.0));
		when(employeeRepository.findAll()).thenReturn(employees);

		employeeService.incrementSalaryByTenPercent();

		verify(employeeRepository, times(1)).saveAll(anyList()); // ✅ this is what Mockito expects
	}

}
