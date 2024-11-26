package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Employee;
import com.shiraku.javacodespring.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<EmployeeProjection> findByDepartmentId(Long departmentId);
}
