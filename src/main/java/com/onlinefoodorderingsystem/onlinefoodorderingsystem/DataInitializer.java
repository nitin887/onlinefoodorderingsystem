package com.onlinefoodorderingsystem.onlinefoodorderingsystem;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.*;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DeliveryRepository deliveryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Default Users
        User admin = createAndSaveUser("Admin User", "admin@example.com", "admin123", "ADMIN", "Admin Address", "1112223333");
        User user = createAndSaveUser("Regular User", "user@example.com", "user123", "USER", "User Address", "4445556666");
        User deliveryPerson = createAndSaveUser("Delivery Person", "delivery@example.com", "delivery123", "DELIVERY", "Delivery Address", "7778889999");

        // 2. Create a Restaurant
        Restaurant restaurant = createAndSaveRestaurant("The Food Place", "123 Food Street", "A great place to eat.");

        // 3. Create Menu Items
        MenuItem burger = createAndSaveMenuItem(restaurant, "Classic Burger", "A juicy beef burger.", 12.99);
        MenuItem pizza = createAndSaveMenuItem(restaurant, "Cheesy Pizza", "A delicious cheesy pizza.", 15.99);

        // 4. Create an Order
        if (orderRepository.count() == 0) {
            Order order = new Order();
            order.setUser(user);
            order.setRestaurant(restaurant);
            order.setOrderPlacedAtTime(LocalDateTime.now());
            order.setTotalAmount(burger.getPrice() + pizza.getPrice());
            Order savedOrder = orderRepository.save(order);

            // 5. Create Order Items
            OrderItem orderItem1 = createAndSaveOrderItem(savedOrder, burger, 1);
            OrderItem orderItem2 = createAndSaveOrderItem(savedOrder, pizza, 1);
            savedOrder.setOrderItems(List.of(orderItem1, orderItem2));

            // 6. Create a Delivery
            createAndSaveDelivery(savedOrder, deliveryPerson);
        }
    }

    private User createAndSaveUser(String name, String email, String password, String role, String address, String phone) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(password));
            newUser.setRoles(role);
            newUser.setAddress(address);
            newUser.setPhoneNumber(phone);
            return userRepository.save(newUser);
        });
    }

    private Restaurant createAndSaveRestaurant(String name, String address, String description) {
        return restaurantRepository.findByName(name).orElseGet(() -> {
            Restaurant newRestaurant = new Restaurant();
            newRestaurant.setName(name);
            newRestaurant.setAddress(address);
            newRestaurant.setDescription(description);
            return restaurantRepository.save(newRestaurant);
        });
    }

    private MenuItem createAndSaveMenuItem(Restaurant restaurant, String name, String description, double price) {
        return menuItemRepository.findByName(name).orElseGet(() -> {
            MenuItem newMenuItem = new MenuItem();
            newMenuItem.setRestaurant(restaurant);
            newMenuItem.setName(name);
            newMenuItem.setDescription(description);
            newMenuItem.setPrice(price);
            return menuItemRepository.save(newMenuItem);
        });
    }

    private OrderItem createAndSaveOrderItem(Order order, MenuItem menuItem, int quantity) {
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setOrder(order);
        newOrderItem.setMenuItem(menuItem);
        newOrderItem.setQuantity(quantity);
        return orderItemRepository.save(newOrderItem);
    }

    private void createAndSaveDelivery(Order order, User deliveryPerson) {
        if (deliveryRepository.findByOrder(order).isEmpty()) {
            Delivery newDelivery = new Delivery();
            newDelivery.setOrder(order);
            newDelivery.setDeliveryPerson(deliveryPerson);
            newDelivery.setDeliveryStatus("PENDING");
            newDelivery.setEstimatedDeliveryTime(LocalDateTime.now().plusHours(1));
            deliveryRepository.save(newDelivery);
        }
    }
}
