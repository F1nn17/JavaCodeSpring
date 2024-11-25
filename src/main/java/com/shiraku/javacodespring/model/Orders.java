package com.shiraku.javacodespring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "\"orders\"")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @OneToMany
    private List<Product> products;

    private String orderDate;

    private String shippingAddress;

    @NotNull
    private Double totalPrice;

    private String orderStatus;
}
