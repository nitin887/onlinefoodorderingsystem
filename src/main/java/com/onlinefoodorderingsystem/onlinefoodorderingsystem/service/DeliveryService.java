package com.onlinefoodorderingsystem.onlinefoodorderingsystem.service;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.DeliveryDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Delivery;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Order;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.User;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.DeliveryRepository;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.OrderRepository;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public DeliveryDTO createDelivery(DeliveryDTO deliveryDTO) {
        Order order = orderRepository.findById(deliveryDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        User deliveryPerson = userRepository.findById(deliveryDTO.getDeliveryPersonId())
                .orElseThrow(() -> new RuntimeException("Delivery Person not found"));

        Delivery delivery = new Delivery();
        delivery.setOrder(order);
        delivery.setDeliveryPerson(deliveryPerson);
        delivery.setDeliveryStatus(deliveryDTO.getDeliveryStatus());
        delivery.setEstimatedDeliveryTime(deliveryDTO.getEstimatedDeliveryTime());
        Delivery savedDelivery = deliveryRepository.save(delivery);
        return toDto(savedDelivery);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DeliveryDTO getDeliveryById(Long id) {
        return deliveryRepository.findById(id).map(this::toDto).orElse(null);
    }

    private DeliveryDTO toDto(Delivery delivery) {
        DeliveryDTO dto = new DeliveryDTO();
        dto.setId(delivery.getId());
        dto.setOrderId(delivery.getOrder().getId());
        dto.setDeliveryPersonId(delivery.getDeliveryPerson().getId());
        dto.setDeliveryStatus(delivery.getDeliveryStatus());
        dto.setEstimatedDeliveryTime(delivery.getEstimatedDeliveryTime());
        return dto;
    }
}
