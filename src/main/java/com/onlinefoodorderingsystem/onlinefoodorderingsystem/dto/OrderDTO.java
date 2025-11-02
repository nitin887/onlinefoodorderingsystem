package com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long restaurantId;
    private LocalDateTime orderPlacedAtTime;
    private Double totalAmount;
    private List<OrderItemDTO> orderItems;
}
