package com.kgisl.sb1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kgisl.sb1.entity.Employee;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Sb1Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {
    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void createEmployeetest() {
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setAge(30);
        HttpEntity<Employee> entity = new HttpEntity<>(employee);
        ResponseEntity<Employee> response = restTemplate.exchange(
                createURLWithPort("/employee"),
                HttpMethod.POST, entity, Employee.class);
        Employee createdEmployee = response.getBody();
        System.out.println("Actual response: " + response.getBody());
        System.out.println("*************************Create Employee*****************");
        assertTrue(createdEmployee != null && createdEmployee.getId() > 0);
        assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void getAllEmployeetest() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/employee/"),
                HttpMethod.GET, entity, String.class);
        System.out.println("Actual Response: " + response.getBody());
        System.out.println("******************getAllEmployee********************");
    }

    @Test
    public void getEmployeeByIdtest() {
        // Assume you have an existing employee with ID 1 in your database
        int employeeIdToRetrieve = 6;
        ResponseEntity<Employee> response = restTemplate.getForEntity(
                createURLWithPort("/employee/" + employeeIdToRetrieve),
                Employee.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Employee retrievedEmployee = response.getBody();
        System.out.println(retrievedEmployee);
        assertEquals(employeeIdToRetrieve, retrievedEmployee.getId());
        System.out.println("***************get Employee By Id***********************");
    }

    @Test
    public void updateEmployeetest() {
        int employeeIdToUpdate = 1;

        // Create an updated employee object
        Employee updatedEmployee = new Employee();
        updatedEmployee.setName("UpdatedName");
        updatedEmployee.setAge(25);
        HttpEntity<Employee> requestEntity = new HttpEntity<>(updatedEmployee, null);

        ResponseEntity<Employee> response = restTemplate.exchange(
                createURLWithPort("/employee/" + employeeIdToUpdate),
                HttpMethod.PUT,
                requestEntity,
                Employee.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Employee updatedEmployeeResponse = response.getBody();
        System.out.println(updatedEmployeeResponse);
        assertEquals(employeeIdToUpdate, updatedEmployeeResponse.getId());
        assertEquals("UpdatedName", updatedEmployeeResponse.getName());
        assertEquals(25, updatedEmployeeResponse.getAge());
        System.out.println("**********************Update Employee************************");
    }

    @Test
    public void deleteEmployeetest() {
        int EmployeeIdToDelete = 3;
        ResponseEntity<Void> response = restTemplate.exchange(
                createURLWithPort("/employee/" + EmployeeIdToDelete),
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<Employee> deletedEmployeeResponse = restTemplate.getForEntity(
                createURLWithPort("/employee/" + EmployeeIdToDelete),
                Employee.class);

        assertEquals(HttpStatus.NOT_FOUND, deletedEmployeeResponse.getStatusCode());
        assertNull(deletedEmployeeResponse.getBody());
        System.out.println("*******************Delete employee by Id**************");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
