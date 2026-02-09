package com.bank.app.controller;

import com.bank.app.dto.AuthResponseDto;
import com.bank.app.dto.LoginDto;
import com.bank.app.dto.UserRegistrationDto;
import com.bank.app.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        AuthResponseDto response = authService.register(registrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and get JWT token")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        AuthResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
