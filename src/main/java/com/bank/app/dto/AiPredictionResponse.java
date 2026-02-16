package com.bank.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiPredictionResponse {
    private String description;
    private Integer category_id;
    private String category_name;
}
