package com.nandhu.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nandhu.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

