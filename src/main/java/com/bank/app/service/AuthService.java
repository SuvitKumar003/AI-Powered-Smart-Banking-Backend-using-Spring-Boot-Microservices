package com.bank.app.service;

import com.bank.app.dto.AuthResponseDto;
import com.bank.app.dto.LoginDto;
import com.bank.app.dto.UserRegistrationDto;
import com.bank.app.model.User;
import com.bank.app.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponseDto register(UserRegistrationDto registrationDto) {
        User user = userService.registerUser(registrationDto);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponseDto(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getAccountBalance());
    }

    public AuthResponseDto login(LoginDto loginDto) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));

        // Generate JWT token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        // Get user info
        User user = userService.getUserByUsername(userDetails.getUsername());

        return new AuthResponseDto(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getAccountBalance());
    }
}
