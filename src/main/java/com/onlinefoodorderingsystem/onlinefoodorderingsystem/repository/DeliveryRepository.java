package com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Delivery;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<Delivery> findByOrder(Order order);
}
