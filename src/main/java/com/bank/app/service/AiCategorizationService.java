package com.bank.app.service;

import com.bank.app.dto.AiPredictionRequest;
import com.bank.app.dto.AiPredictionResponse;
import com.bank.app.model.TransactionCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiCategorizationService {

    private final RestTemplate restTemplate;
    private static final String AI_SERVICE_URL = "http://localhost:8000/predict";

    /**
     * AI-powered transaction categorization using real PyTorch model (Python
     * Service)
     */
    public TransactionCategory categorizeTransaction(String description) {
        if (description == null || description.trim().isEmpty()) {
            return TransactionCategory.OTHER;
        }

        try {
            AiPredictionRequest request = new AiPredictionRequest(description);
            AiPredictionResponse response = restTemplate.postForObject(AI_SERVICE_URL, request,
                    AiPredictionResponse.class);

            if (response != null && response.getCategory_name() != null) {
                return mapToCategory(response.getCategory_name());
            }
        } catch (Exception e) {
            log.error("AI Service failed, using fallback: {}", e.getMessage());
        }

        return TransactionCategory.OTHER;
    }

    private TransactionCategory mapToCategory(String pythonCategory) {
        return switch (pythonCategory) {
            case "Food & Dining" -> TransactionCategory.FOOD;
            case "Transportation" -> TransactionCategory.TRAVEL;
            case "Utilities" -> TransactionCategory.BILLS;
            case "Shopping" -> TransactionCategory.SHOPPING;
            case "Entertainment" -> TransactionCategory.ENTERTAINMENT;
            case "Health & Wellness" -> TransactionCategory.HEALTHCARE;
            case "Income" -> TransactionCategory.SALARY;
            default -> TransactionCategory.OTHER;
        };
    }
}
