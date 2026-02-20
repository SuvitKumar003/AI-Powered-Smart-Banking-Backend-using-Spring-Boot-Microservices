package com.bank.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiPredictionResponse {
    private String description;

    @JsonProperty("category_id")
    private Integer category_id;

    @JsonProperty("category_name")
    private String category_name;
}
