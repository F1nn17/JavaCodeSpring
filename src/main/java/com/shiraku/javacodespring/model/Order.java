package com.shiraku.javacodespring.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.shiraku.javacodespring.views.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(Views.UserDetails.class)
    private String product;

    @JsonView(Views.UserDetails.class)
    private Double amount;

    @JsonView(Views.UserDetails.class)
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
