package com.bank.app.controller;

import com.bank.app.dto.TransactionDto;
import com.bank.app.model.TransactionCategory;
import com.bank.app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Transactions", description = "Transaction management APIs")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "Create transaction", description = "Create a new transaction with AI categorization")
    public ResponseEntity<TransactionDto> createTransaction(
            @Valid @RequestBody TransactionDto transactionDto,
            Authentication authentication) {
        String username = authentication.getName();
        TransactionDto created = transactionService.createTransaction(username, transactionDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get transaction history", description = "Get all transactions for current user")
    public ResponseEntity<List<TransactionDto>> getTransactionHistory(Authentication authentication) {
        String username = authentication.getName();
        List<TransactionDto> transactions = transactionService.getTransactionHistory(username);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Filter by category", description = "Get transactions filtered by category")
    public ResponseEntity<List<TransactionDto>> getTransactionsByCategory(
            @PathVariable TransactionCategory category,
            Authentication authentication) {
        String username = authentication.getName();
        List<TransactionDto> transactions = transactionService.filterByCategory(username, category);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Filter by date range", description = "Get transactions within a date range")
    public ResponseEntity<List<TransactionDto>> getTransactionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end,
            Authentication authentication) {
        String username = authentication.getName();
        List<TransactionDto> transactions = transactionService.filterByDateRange(username, start, end);
        return ResponseEntity.ok(transactions);
    }
}
