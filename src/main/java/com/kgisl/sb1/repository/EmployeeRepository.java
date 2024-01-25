package com.kgisl.sb1.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.kgisl.sb1.entity.Employee;
 
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    
}