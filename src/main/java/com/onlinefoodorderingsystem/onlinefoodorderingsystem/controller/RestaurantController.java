package com.onlinefoodorderingsystem.onlinefoodorderingsystem.controller;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.RestaurantDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.service.RestaurantService;
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
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurant Controller", description = "APIs for managing restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant. Requires ADMIN role.")
    @ApiResponse(responseCode = "201", description = "Restaurant created successfully")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO createdRestaurant = restaurantService.createRestaurant(restaurantDTO);
        return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all restaurants", description = "Retrieves a list of all restaurants. Accessible by all authenticated users.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of restaurants")
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'DELIVERY')")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }
}
