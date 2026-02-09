package com.bank.app.dto;

import com.bank.app.model.TransactionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInsightDto {

    private TransactionCategory category;
    private String categoryName;
    private BigDecimal totalAmount;
    private Long transactionCount;
    private Double percentage;
}
