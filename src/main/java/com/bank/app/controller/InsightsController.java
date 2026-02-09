package com.bank.app.controller;

import com.bank.app.dto.CategoryInsightDto;
import com.bank.app.service.InsightsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Insights & Analytics", description = "Financial insights and analytics APIs")
public class InsightsController {

    private final InsightsService insightsService;

    @GetMapping("/category-wise")
    @Operation(summary = "Category-wise spending", description = "Get spending breakdown by category")
    public ResponseEntity<List<CategoryInsightDto>> getCategoryWiseSpending(Authentication authentication) {
        String username = authentication.getName();
        List<CategoryInsightDto> insights = insightsService.getCategoryWiseSpending(username);
        return ResponseEntity.ok(insights);
    }

    @GetMapping("/monthly-summary")
    @Operation(summary = "Monthly summary", description = "Get monthly spending summary")
    public ResponseEntity<Map<String, Object>> getMonthlySummary(Authentication authentication) {
        String username = authentication.getName();
        Map<String, Object> summary = insightsService.getMonthlySummary(username);
        return ResponseEntity.ok(summary);
    }
}
