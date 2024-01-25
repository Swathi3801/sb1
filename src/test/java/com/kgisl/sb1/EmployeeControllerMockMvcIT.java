package com.kgisl.sb1;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.kgisl.sb1.entity.Employee;
import com.kgisl.sb1.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgisl.sb1.controller.EmployeeController;

/**
 * TeamControllerMockMVCIT
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = EmployeeController.class)
public class EmployeeControllerMockMvcIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    public Employee employee1 = new EmployeeBuilder().setId(1).setName("Josh").setAge(22).build();
    public Employee employee2 = new EmployeeBuilder().setId(2).setName("Swathi").setAge(23).build();
    public Employee updatedEmployee = new EmployeeBuilder().setId(1).setName("Joshika").setAge(25).build();

    @Test
    public void getAll() throws Exception {
        List<Employee> alist1 = new ArrayList<Employee>();
        alist1.add(employee1);
        alist1.add(employee2);
        given(employeeService.getAllEmployees()).willReturn(alist1);
        mockMvc.perform(get("/employee/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        content().json("[{'id': 1,'name': 'Josh','age': 22},{'id': 2,'name': 'Swathi','age' : 23}]"));
        System.out.println(alist1.toString());
        System.out.println("****************getall***************");
    }

    @Test
    public void getEmployeeById() throws Exception {
        List<Employee> alist1 = new ArrayList<Employee>();
        alist1.add(employee1);
        alist1.add(employee2);
        when(employeeService.getEmployeeById(employee1.getId())).thenReturn(employee1);
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee1)))
                .andExpect(status().isCreated());
        System.out.println("********************getEmployeeById***********************");
    }

    @Test
public void updateEmployee() throws Exception {
    // Mocking the service method for updating an employee
    when(employeeService.updateEmployee(eq(1), any(Employee.class)))
            .thenReturn(updatedEmployee);
    // System.out.println(updatedEmployee);
    // Performing the request to update an employee
    mockMvc.perform(put("/employee/1", updatedEmployee.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(updatedEmployee)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(asJsonString(updatedEmployee)));
    System.out.println("********************updateEmployee***********************");
}

    @Test
    public void deleteByID() throws Exception {
        List<Employee> alist = new ArrayList<Employee>();
        alist.add(employee1);
        when(employeeService.getEmployeeById(employee1.getId())).thenReturn(employee1);
        mockMvc.perform(delete("/employee/1",
                employee1.getId())).andExpect(status().is2xxSuccessful());
        System.out.println("******************Delete byID**************************");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
