package com.nandhu.employee.controller;

//import com.nandhu.employee.controller.EmployeeController;
import com.nandhu.employee.model.Employee;
import com.nandhu.employee.service.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee("John", "IT", 5000.0);
        when(employeeService.saveEmployee(employee)).thenReturn(employee);

        Employee result = employeeController.addEmployee(employee);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(employeeService).saveEmployee(employee);
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee("John", "IT", 5000.0);
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        Employee result = employeeController.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee("John", "HR", 6000.0);
        when(employeeService.updateEmployee(1L, employee)).thenReturn(employee);

        Employee result = employeeController.updateEmployee(1L, employee);

        assertNotNull(result);
        assertEquals("HR", result.getDepartment());
        verify(employeeService).updateEmployee(1L, employee);
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeService).deleteEmployee(1L);

        employeeController.deleteEmployee(1L);

        verify(employeeService).deleteEmployee(1L);
    }

    @Test
    void testGetAllEmployees() {
        Employee emp1 = new Employee("John", "IT", 5000.0);
        Employee emp2 = new Employee("Jane", "HR", 6000.0);
        List<Employee> employees = Arrays.asList(emp1, emp2);
        Pageable pageable = PageRequest.of(0, 5);
        Page<Employee> page = new PageImpl<>(employees, pageable, employees.size());

        when(employeeService.getAllEmployees(0, 5, "id")).thenReturn(page);

        Page<Employee> result = employeeController.getAllEmployees(0, 5, "id");

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(employeeService).getAllEmployees(0, 5, "id");
    }

    @Test
    void testIncrementSalary() {
        doNothing().when(employeeService).incrementSalaryByTenPercent();

        employeeController.incrementSalary();

        verify(employeeService).incrementSalaryByTenPercent();
    }
}
