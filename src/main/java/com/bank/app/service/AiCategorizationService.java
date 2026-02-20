package com.bank.app.service;

import com.bank.app.dto.AiPredictionRequest;
import com.bank.app.dto.AiPredictionResponse;
import com.bank.app.dto.FraudCheckResponse;
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
                log.info("AI Service predicted: {} for {}", response.getCategory_name(), description);
                return mapToCategory(response.getCategory_name());
            } else {
                log.warn("AI Service returned empty response or null category for: {}", description);
            }
        } catch (Exception e) {
            log.error("AI Service call failed for '{}': {}", description, e.getMessage());
        }

        return TransactionCategory.OTHER;
    }

    public FraudCheckResponse checkFraud(String description, Double amount) {
        try {
            var request = new java.util.HashMap<String, Object>();
            request.put("description", description);
            request.put("amount", amount);

            var response = restTemplate.postForObject("http://localhost:8000/fraud-check", request,
                    FraudCheckResponse.class);
            return response != null ? response : new FraudCheckResponse(amount, "LOW", 0.05);
        } catch (Exception e) {
            log.error("AI Fraud Check failed: {}", e.getMessage());
            return new FraudCheckResponse(amount, "LOW", 0.05);
        }
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
