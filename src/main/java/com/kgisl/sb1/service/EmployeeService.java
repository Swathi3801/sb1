package com.kgisl.sb1.service;

import java.util.List;

import com.kgisl.sb1.entity.Employee;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    Employee updateEmployee(int id, Employee updatedEmployee);

    void deleteEmployee(int id);
}
