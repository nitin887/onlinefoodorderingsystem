package com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private Long menuItemId;
    private Integer quantity;
}
