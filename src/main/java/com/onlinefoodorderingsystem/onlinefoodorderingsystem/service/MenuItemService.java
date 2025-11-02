package com.onlinefoodorderingsystem.onlinefoodorderingsystem.service;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.MenuItemDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.MenuItem;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.entity.Restaurant;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.MenuItemRepository;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        Restaurant restaurant = restaurantRepository.findById(menuItemDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(restaurant);
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return toDto(savedMenuItem);
    }

    public List<MenuItemDTO> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findAll().stream()
                .filter(item -> item.getRestaurant().getId().equals(restaurantId))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MenuItemDTO toDto(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setRestaurantId(menuItem.getRestaurant().getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        return dto;
    }
}
