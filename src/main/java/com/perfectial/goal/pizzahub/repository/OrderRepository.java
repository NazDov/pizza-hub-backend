package com.perfectial.goal.pizzahub.repository;

import com.perfectial.goal.pizzahub.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
