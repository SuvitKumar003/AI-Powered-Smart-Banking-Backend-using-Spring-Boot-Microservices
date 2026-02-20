package com.bank.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckResponse {
    private Double amount;
    private String risk_level;
    private Double risk_score;
}
