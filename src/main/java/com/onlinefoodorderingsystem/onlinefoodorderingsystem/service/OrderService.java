package com.onlinefoodorderingsystem.onlinefoodorderingsystem.service;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.OrderDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.OrderItemDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.MenuItem;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Order;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.OrderItem;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Restaurant;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.User;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.MenuItemRepository;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.OrderRepository;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.RestaurantRepository;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Restaurant restaurant = restaurantRepository.findById(orderDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setOrderPlacedAtTime(LocalDateTime.now());

        double totalAmount = 0.0;
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(itemDTO -> {
            MenuItem menuItem = menuItemRepository.findById(itemDTO.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu Item not found"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDTO.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());

        for (OrderItem item : orderItems) {
            totalAmount += item.getMenuItem().getPrice() * item.getQuantity();
        }
        order.setTotalAmount(totalAmount);
        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return toDto(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id).map(this::toDto).orElse(null);
    }

    private OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setRestaurantId(order.getRestaurant().getId());
        dto.setOrderPlacedAtTime(order.getOrderPlacedAtTime());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderItems(order.getOrderItems().stream().map(this::toOrderItemDto).collect(Collectors.toList()));
        return dto;
    }

    private OrderItemDTO toOrderItemDto(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setOrderId(orderItem.getOrder().getId());
        dto.setMenuItemId(orderItem.getMenuItem().getId());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }
}
