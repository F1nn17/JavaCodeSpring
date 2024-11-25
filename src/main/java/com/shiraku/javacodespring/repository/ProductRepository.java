package com.shiraku.javacodespring.repository;

import com.shiraku.javacodespring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
