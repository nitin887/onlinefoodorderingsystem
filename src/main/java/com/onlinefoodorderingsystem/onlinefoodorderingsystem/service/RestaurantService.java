package com.onlinefoodorderingsystem.onlinefoodorderingsystem.service;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.RestaurantDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Restaurant;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setDescription(restaurantDTO.getDescription());
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return toDto(savedRestaurant);
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private RestaurantDTO toDto(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setDescription(restaurant.getDescription());
        return dto;
    }
}
