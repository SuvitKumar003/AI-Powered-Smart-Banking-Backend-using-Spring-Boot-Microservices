package com.bank.app.service;

import com.bank.app.dto.CategoryInsightDto;
import com.bank.app.exception.ResourceNotFoundException;
import com.bank.app.model.TransactionCategory;
import com.bank.app.model.User;
import com.bank.app.repository.TransactionRepository;
import com.bank.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsightsService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public List<CategoryInsightDto> getCategoryWiseSpending(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Object[]> results = transactionRepository.getCategoryWiseSpending(user.getId());

        BigDecimal totalSpending = BigDecimal.ZERO;
        Map<TransactionCategory, CategoryInsightDto> insightMap = new HashMap<>();

        // Process results
        for (Object[] result : results) {
            TransactionCategory category = (TransactionCategory) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            Long count = (Long) result[2];

            totalSpending = totalSpending.add(amount);

            CategoryInsightDto insight = new CategoryInsightDto();
            insight.setCategory(category);
            insight.setCategoryName(category.getDisplayName());
            insight.setTotalAmount(amount);
            insight.setTransactionCount(count);

            insightMap.put(category, insight);
        }

        // Calculate percentages
        List<CategoryInsightDto> insights = new ArrayList<>();
        for (CategoryInsightDto insight : insightMap.values()) {
            if (totalSpending.compareTo(BigDecimal.ZERO) > 0) {
                double percentage = insight.getTotalAmount()
                        .divide(totalSpending, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"))
                        .doubleValue();
                insight.setPercentage(percentage);
            } else {
                insight.setPercentage(0.0);
            }
            insights.add(insight);
        }

        return insights;
    }

    public Map<String, Object> getMonthlySummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);

        BigDecimal monthlySpending = transactionRepository.getTotalSpending(user.getId(), startOfMonth);
        if (monthlySpending == null) {
            monthlySpending = BigDecimal.ZERO;
        }

        BigDecimal monthlyIncome = transactionRepository.getTotalIncome(user.getId(), startOfMonth);
        if (monthlyIncome == null) {
            monthlyIncome = BigDecimal.ZERO;
        }

        Map<String, Object> summary = new HashMap<>();
        summary.put("month", LocalDateTime.now().getMonth().toString());
        summary.put("year", LocalDateTime.now().getYear());
        summary.put("totalSpending", monthlySpending);
        summary.put("totalIncome", monthlyIncome);
        summary.put("currentBalance", user.getAccountBalance());
        summary.put("totalTransactions", user.getTransactions().size());

        return summary;
    }
}
