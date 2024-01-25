package com.kgisl.sb1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.kgisl.sb1.controller.EmployeeController;
import com.kgisl.sb1.service.EmployeeService;
import com.kgisl.sb1.entity.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    public static List<Employee> expected;

    Employee employee1 = employee(1, "josh", 22);
    Employee employee2 = employee(2, "swathi", 23);
   
    private Employee employee(int id, String name, int age) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setName(name);
        employee.setAge(age);
        return employee;
    }

    @Test
    public void getAllEmployeeTest() {
        System.out.println("-----------getallEmployees-------------");
        expected = Arrays.asList(employee1, employee2);
        System.out.println(expected);
        when(employeeService.getAllEmployees()).thenReturn(expected);
        ResponseEntity<List<Employee>> actual = employeeController.getAllEmployees();
        System.out.println(actual);
        System.out.println(actual.getBody());
        assertNotNull(actual);
        assertEquals(expected, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void getEmployeeByIdTest() {
        System.out.println("-----------GetEmployeeById-----------");
        int id = 2;
        when(employeeService.getEmployeeById(id)).thenReturn(employee2);
        ResponseEntity<Employee> actual = employeeController.getEmployeeById(id);
        assertNotNull(actual);
        System.out.println(actual);
    }

    @Test
    public void createEmployeeTest() {  // Change the method name
        when(employeeService.createEmployee(employee1)).thenReturn(employee1);  // Change the entity type
        employeeController.createEmployee(employee1);  // Change the controller method
        System.out.println("------------------createEmployeeTest-------------");  // Change the message
    }

    @Test
    public void updatedEmployeeTest() {
        System.out.println("--------------updatedEmployee-------------------");
        int id = 1;
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(id);
        updatedEmployee.setName("Indhu");
        updatedEmployee.setAge(25);
        when(employeeService.updateEmployee(id, updatedEmployee)).thenReturn(updatedEmployee);
        ResponseEntity<Employee> actual = employeeController.updateEmployee(id, updatedEmployee);
        assertNotNull(actual);
        System.out.println(actual);
        assertEquals(updatedEmployee, actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(employeeService).updateEmployee(id, updatedEmployee);
    }


    @Test
    public void deleteEmployeeTest() {
        System.out.println("------------deleteEmployee------------");
        int id = 1;
        when(employeeService.getEmployeeById(id)).thenReturn(employee1);
        employeeController.deleteEmployee(id);
        verify(employeeService).deleteEmployee(id);
    }
}
