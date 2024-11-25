package com.shiraku.javacodespring.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.shiraku.javacodespring.views.Views;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.UserSummary.class)
    @NotEmpty(message = "Name is required")
    private String name;

    @JsonView(Views.UserSummary.class)
    @Email(message = "Invalid email")
    @NotEmpty(message = "Email is required")
    private String email;

    @JsonView(Views.UserDetails.class)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
}
