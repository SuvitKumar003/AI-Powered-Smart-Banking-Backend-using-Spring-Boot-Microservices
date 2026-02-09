package com.bank.app.service;

import com.bank.app.dto.UserProfileDto;
import com.bank.app.dto.UserRegistrationDto;
import com.bank.app.exception.ResourceNotFoundException;
import com.bank.app.exception.UserAlreadyExistsException;
import com.bank.app.model.User;
import com.bank.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRegistrationDto registrationDto) {
        // Check if username exists
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        // Check if email exists
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered");
        }

        // Create new user
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setFullName(registrationDto.getFullName());
        user.setPhoneNumber(registrationDto.getPhoneNumber());
        user.setAccountBalance(new BigDecimal("10000.00")); // Initial balance
        user.setEnabled(true);
        user.setRole("USER");

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserProfileDto getUserProfile(String username) {
        User user = getUserByUsername(username);

        UserProfileDto profile = new UserProfileDto();
        profile.setId(user.getId());
        profile.setUsername(user.getUsername());
        profile.setEmail(user.getEmail());
        profile.setFullName(user.getFullName());
        profile.setPhoneNumber(user.getPhoneNumber());
        profile.setAccountBalance(user.getAccountBalance());
        profile.setTotalTransactions((long) user.getTransactions().size());

        return profile;
    }

    @Transactional
    public void updateAccountBalance(Long userId, BigDecimal amount, boolean isCredit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (isCredit) {
            user.setAccountBalance(user.getAccountBalance().add(amount));
        } else {
            user.setAccountBalance(user.getAccountBalance().subtract(amount));
        }

        userRepository.save(user);
    }
}
