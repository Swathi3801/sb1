package com.kgisl.sb1;

import com.kgisl.sb1.entity.Employee;

public class EmployeeBuilder {

    
    Employee employee=new Employee();

    public EmployeeBuilder setId(int id) {
        employee.setId(id);
        return this;
    }

    public EmployeeBuilder setName(String name) {
        employee.setName(name);
        return this;
    }

    public EmployeeBuilder setAge(int age) {
        employee.setAge(age);
        return this;
    }

    public Employee build() {
        return employee;
    }
}
