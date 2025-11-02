package com.onlinefoodorderingsystem.onlinefoodorderingsystem.controller;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.DeliveryDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.service.DeliveryService;
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
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Tag(name = "Delivery Controller", description = "APIs for managing deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Create a new delivery", description = "Creates a new delivery record. Requires ADMIN role.")
    @ApiResponse(responseCode = "201", description = "Delivery created successfully")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DeliveryDTO> createDelivery(@RequestBody DeliveryDTO deliveryDTO) {
        DeliveryDTO createdDelivery = deliveryService.createDelivery(deliveryDTO);
        return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all deliveries", description = "Retrieves a list of all deliveries. Requires ADMIN or DELIVERY role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of deliveries")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DELIVERY')")
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveries() {
        List<DeliveryDTO> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

    @Operation(summary = "Get a delivery by ID", description = "Retrieves a specific delivery by its unique ID. Requires ADMIN or DELIVERY role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved delivery")
    @ApiResponse(responseCode = "404", description = "Delivery not found")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DELIVERY')")
    public ResponseEntity<DeliveryDTO> getDeliveryById(@PathVariable Long id) {
        DeliveryDTO deliveryDTO = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(deliveryDTO);
    }
}
