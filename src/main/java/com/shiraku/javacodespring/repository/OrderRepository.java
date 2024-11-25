package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
