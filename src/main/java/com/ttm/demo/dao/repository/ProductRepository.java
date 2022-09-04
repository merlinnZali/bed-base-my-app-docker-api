package com.ttm.demo.dao.repository;

import com.ttm.demo.domaine.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
