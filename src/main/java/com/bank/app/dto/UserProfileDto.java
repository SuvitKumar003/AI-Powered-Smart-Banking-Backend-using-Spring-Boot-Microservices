package com.bank.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private BigDecimal accountBalance;
    private Long totalTransactions;
}
