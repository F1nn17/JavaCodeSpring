package com.shiraku.javacodespring.controller;

import com.shiraku.javacodespring.model.Department;
import com.shiraku.javacodespring.model.Employee;
import com.shiraku.javacodespring.projection.EmployeeProjection;
import com.shiraku.javacodespring.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        return ResponseEntity.ok(employeeService.saveDepartment(department));
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(employeeService.getAllDepartments());
    }

    @GetMapping("/departments/{id}/employees")
    public ResponseEntity<List<EmployeeProjection>> getEmployeesByDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(id));
    }
}
