package com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryDTO {
    private Long id;
    private Long orderId;
    private Long deliveryPersonId;
    private String deliveryStatus;
    private LocalDateTime estimatedDeliveryTime;
}
