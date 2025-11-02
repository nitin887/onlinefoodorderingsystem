package com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
