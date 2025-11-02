package com.onlinefoodorderingsystem.onlinefoodorderingsystem.controller;

import com.onlinefoodorderingsystem.onlinefoodorderingsystem.dto.UserDTO;
import com.onlinefoodorderingsystem.onlinefoodorderingsystem.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the USER role. This endpoint is publicly accessible.")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userDTO.setRoles("USER"); // Force role to USER for this endpoint
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Register a new admin user", description = "Creates a new user account with the ADMIN role. Requires ADMIN role.")
    @ApiResponse(responseCode = "201", description = "Admin user created successfully")
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createAdminUser(@RequestBody UserDTO userDTO) {
        userDTO.setRoles("ADMIN"); // Force role to ADMIN for this endpoint
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a user by ID", description = "Retrieves a specific user by their unique ID. Requires USER or ADMIN role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user")
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users. Requires ADMIN role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
