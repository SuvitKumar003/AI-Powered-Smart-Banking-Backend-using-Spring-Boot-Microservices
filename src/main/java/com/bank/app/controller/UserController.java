package com.bank.app.controller;

import com.bank.app.dto.UserProfileDto;
import com.bank.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Management", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Get user profile", description = "Get current user's profile information")
    public ResponseEntity<UserProfileDto> getProfile(Authentication authentication) {
        String username = authentication.getName();
        UserProfileDto profile = userService.getUserProfile(username);
        return ResponseEntity.ok(profile);
    }
}
