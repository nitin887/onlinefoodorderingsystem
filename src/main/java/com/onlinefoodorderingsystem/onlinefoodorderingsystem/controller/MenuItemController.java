package com.onlinefoodorderingsystem.onlinefoodorderingsystem.controller;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.MenuItemDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.service.MenuItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
@Tag(name = "Menu Item Controller", description = "APIs for managing menu items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    @Operation(summary = "Create a new menu item", description = "Creates a new menu item for a restaurant. Requires ADMIN role.")
    @ApiResponse(responseCode = "201", description = "Menu item created successfully")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItemDTO> createMenuItem(@RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO createdMenuItem = menuItemService.createMenuItem(menuItemDTO);
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }

    @Operation(summary = "Get menu items by restaurant ID", description = "Retrieves a list of menu items for a specific restaurant. Accessible by all authenticated users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of menu items")
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'DELIVERY')")
    public ResponseEntity<List<MenuItemDTO>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItemDTO> menuItems = menuItemService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(menuItems);
    }
}
