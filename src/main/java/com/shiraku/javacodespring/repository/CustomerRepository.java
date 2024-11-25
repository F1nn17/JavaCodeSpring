package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
