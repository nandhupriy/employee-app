package com.nandhu.employee.service;

import java.util.List;
import org.springframework.data.domain.Page;

import com.nandhu.employee.model.Employee;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Page<Employee> getAllEmployees(int page, int size, String sortBy);//paginating&sorting
    void incrementSalaryByTenPercent();//salary increment using multi-threading


}
