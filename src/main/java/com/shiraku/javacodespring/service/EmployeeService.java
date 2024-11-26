package com.shiraku.javacodespring.service;

import com.shiraku.javacodespring.model.Department;
import com.shiraku.javacodespring.model.Employee;
import com.shiraku.javacodespring.projection.EmployeeProjection;
import com.shiraku.javacodespring.repository.DepartmentRepository;
import com.shiraku.javacodespring.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<EmployeeProjection> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }
}
