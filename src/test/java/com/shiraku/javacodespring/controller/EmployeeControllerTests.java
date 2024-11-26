package com.shiraku.javacodespring.controller;

import com.shiraku.javacodespring.model.Department;
import com.shiraku.javacodespring.model.Employee;
import com.shiraku.javacodespring.projection.EmployeeProjection;
import com.shiraku.javacodespring.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    public void testAddEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPosition("Developer");
        employee.setSalary(75000);
        employee.setDepartment(new Department(1L, "IT"));

        Mockito.when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"position\":\"Developer\",\"salary\":75000,\"department\":{\"id\":1,\"name\":\"IT\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.position").value("Developer"))
                .andExpect(jsonPath("$.salary").value(75000));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        Employee employee1 = new Employee("John", "Doe", "Developer", 75000, new Department(1L, "IT"));
        Employee employee2 = new Employee("Jane", "Smith", "Manager", 90000, new Department(2L, "HR"));

        Mockito.when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/api/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Smith"));
    }

    @Test
    public void testGetEmployeesByDepartment() throws Exception {
        EmployeeProjection projection1 = Mockito.mock(EmployeeProjection.class);
        Mockito.when(projection1.getFullName()).thenReturn("John Doe");
        Mockito.when(projection1.getPosition()).thenReturn("Developer");
        Mockito.when(projection1.getDepartmentName()).thenReturn("IT");

        Mockito.when(employeeService.getEmployeesByDepartment(1L)).thenReturn(Collections.singletonList(projection1));

        mockMvc.perform(get("/api/departments/1/employees")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].position").value("Developer"))
                .andExpect(jsonPath("$[0].departmentName").value("IT"));
    }

    @Test
    public void testAddDepartment() throws Exception {
        Department department = new Department(1L, "IT");

        Mockito.when(employeeService.saveDepartment(any(Department.class))).thenReturn(department);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"IT\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("IT"));
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        Department department1 = new Department(1L, "IT");
        Department department2 = new Department(2L, "HR");

        Mockito.when(employeeService.getAllDepartments()).thenReturn(Arrays.asList(department1, department2));

        mockMvc.perform(get("/api/departments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("IT"))
                .andExpect(jsonPath("$[1].name").value("HR"));
    }
}
