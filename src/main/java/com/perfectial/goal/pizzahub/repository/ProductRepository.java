package com.perfectial.goal.pizzahub.repository;

import com.perfectial.goal.pizzahub.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
