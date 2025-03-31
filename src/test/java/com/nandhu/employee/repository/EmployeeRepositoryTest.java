package com.nandhu.employee.repository;

import com.nandhu.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testSaveEmployee() {
        Employee employee = new Employee("John", "IT", 60000.0);
        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isNotNull();
    }

    @Test
    void testFindById() {
        Employee employee = new Employee("Jane", "HR", 55000.0);
        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> found = employeeRepository.findById(savedEmployee.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Jane");
    }

    @Test
    void testFindAll() {
        Employee e1 = new Employee("Alex", "Sales", 50000.0);
        Employee e2 = new Employee("Mark", "Finance", 70000.0);
        employeeRepository.save(e1);
        employeeRepository.save(e2);

        List<Employee> employees = employeeRepository.findAll();
        assertThat(employees.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testDeleteById() {
        Employee employee = new Employee("Sam", "Admin", 45000.0);
        Employee savedEmployee = employeeRepository.save(employee);

        employeeRepository.deleteById(savedEmployee.getId());
        Optional<Employee> deleted = employeeRepository.findById(savedEmployee.getId());

        assertThat(deleted).isEmpty();
    }
}
