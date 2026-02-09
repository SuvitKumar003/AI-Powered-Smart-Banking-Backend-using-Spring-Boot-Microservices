package com.bank.app.service;

import com.bank.app.model.TransactionCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AiCategorizationService {

    private final Random random = new Random();

    /**
     * AI-powered transaction categorization using rule-based keyword matching
     */
    public TransactionCategory categorizeTransaction(String description) {
        if (description == null || description.trim().isEmpty()) {
            return TransactionCategory.OTHER;
        }

        String lowerDesc = description.toLowerCase();

        // Food & Dining
        if (containsAny(lowerDesc, "restaurant", "food", "cafe", "coffee", "pizza", "burger",
                "swiggy", "zomato", "dominos", "mcdonald", "kfc", "starbucks", "dining",
                "breakfast", "lunch", "dinner", "bakery", "grocery", "supermarket")) {
            return TransactionCategory.FOOD;
        }

        // Travel & Transportation
        if (containsAny(lowerDesc, "uber", "ola", "taxi", "cab", "flight", "airline",
                "train", "metro", "bus", "fuel", "petrol", "diesel", "parking",
                "toll", "travel", "hotel", "booking", "airbnb")) {
            return TransactionCategory.TRAVEL;
        }

        // Bills & Utilities
        if (containsAny(lowerDesc, "electricity", "water", "gas", "internet", "wifi",
                "broadband", "mobile", "phone", "recharge", "bill", "utility",
                "rent", "emi", "loan", "insurance")) {
            return TransactionCategory.BILLS;
        }

        // Shopping
        if (containsAny(lowerDesc, "amazon", "flipkart", "myntra", "shopping", "mall",
                "store", "clothing", "shoes", "fashion", "electronics", "gadget",
                "mobile", "laptop", "purchase", "buy")) {
            return TransactionCategory.SHOPPING;
        }

        // Entertainment
        if (containsAny(lowerDesc, "movie", "cinema", "netflix", "prime", "spotify",
                "hotstar", "youtube", "music", "game", "gaming", "entertainment",
                "concert", "event", "ticket")) {
            return TransactionCategory.ENTERTAINMENT;
        }

        // Healthcare
        if (containsAny(lowerDesc, "hospital", "doctor", "clinic", "medicine", "pharmacy",
                "medical", "health", "treatment", "therapy", "lab", "test")) {
            return TransactionCategory.HEALTHCARE;
        }

        // Education
        if (containsAny(lowerDesc, "school", "college", "university", "course", "education",
                "tuition", "training", "udemy", "coursera", "book", "library")) {
            return TransactionCategory.EDUCATION;
        }

        // Salary & Income
        if (containsAny(lowerDesc, "salary", "income", "payment received", "credited",
                "refund", "cashback", "bonus", "stipend", "wages")) {
            return TransactionCategory.SALARY;
        }

        // Investment
        if (containsAny(lowerDesc, "investment", "mutual fund", "stock", "trading",
                "zerodha", "groww", "smallcase", "sip", "fd", "deposit")) {
            return TransactionCategory.INVESTMENT;
        }

        // Transfer
        if (containsAny(lowerDesc, "transfer", "sent to", "received from", "upi",
                "paytm", "gpay", "phonepe", "payment")) {
            return TransactionCategory.TRANSFER;
        }

        return TransactionCategory.OTHER;
    }

    /**
     * Calculate fraud risk score based on transaction patterns
     */
    public BigDecimal calculateFraudRiskScore(BigDecimal amount, String description, String location) {
        double riskScore = 0.0;

        // High amount transactions have higher risk
        if (amount.compareTo(new BigDecimal("50000")) > 0) {
            riskScore += 0.3;
        } else if (amount.compareTo(new BigDecimal("20000")) > 0) {
            riskScore += 0.15;
        }

        // Suspicious keywords
        if (description != null) {
            String lowerDesc = description.toLowerCase();
            if (containsAny(lowerDesc, "urgent", "verify", "confirm", "lottery",
                    "winner", "prize", "click", "link")) {
                riskScore += 0.4;
            }
        }

        // International locations have slightly higher risk
        if (location != null && containsAny(location.toLowerCase(),
                "international", "foreign", "overseas")) {
            riskScore += 0.1;
        }

        // Add small random variation (simulating ML model uncertainty)
        riskScore += (random.nextDouble() * 0.1);

        // Cap at 1.0
        return BigDecimal.valueOf(Math.min(riskScore, 1.0));
    }

    /**
     * Helper method to check if string contains any of the keywords
     */
    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
