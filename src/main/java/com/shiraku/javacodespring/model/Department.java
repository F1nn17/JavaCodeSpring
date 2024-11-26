package com.shiraku.javacodespring.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Table
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees;

    public Department(long id, String it) {
        this.id = id;
        this.name = it;
    }

    public Department() {

    }
}
