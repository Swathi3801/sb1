package com.kgisl.sb1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kgisl.sb1.entity.Employee;
import com.kgisl.sb1.repository.EmployeeRepository;
@Service
public class EmployeeServiceImpl implements EmployeeService {
       @Autowired
    private EmployeeRepository employeeRepository;
 
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
 
    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
 
    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }
 
    @Override
    public Employee updateEmployee(int id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setName(updatedEmployee.getName());
            employee.setAge(updatedEmployee.getAge());
            // Set other properties as needed
            return employeeRepository.save(employee);
        }
        return null;
    }
 
    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    
}
