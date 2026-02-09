package com.bank.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String token;
    private String tokenType = "Bearer";
    private String username;
    private String email;
    private String fullName;
    private BigDecimal accountBalance;
    private String message;

    public AuthResponseDto(String token, String username, String email, String fullName, BigDecimal accountBalance) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.accountBalance = accountBalance;
        this.message = "Login successful";
    }
}
