package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
