package com.bank.app.service;

import com.bank.app.dto.TransactionDto;
import com.bank.app.dto.FraudCheckResponse;
import com.bank.app.exception.InsufficientBalanceException;
import com.bank.app.exception.ResourceNotFoundException;
import com.bank.app.model.Transaction;
import com.bank.app.model.TransactionCategory;
import com.bank.app.model.TransactionType;
import com.bank.app.model.User;
import com.bank.app.repository.TransactionRepository;
import com.bank.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;
        private final AiCategorizationService aiService;
        private final UserService userService;

        @Transactional
        public TransactionDto createTransaction(String username, TransactionDto transactionDto) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                // Check balance for debit transactions
                if (transactionDto.getType() == TransactionType.DEBIT) {
                        if (user.getAccountBalance().compareTo(transactionDto.getAmount()) < 0) {
                                throw new InsufficientBalanceException("Insufficient account balance");
                        }
                }

                // AI-powered categorization
                TransactionCategory category = aiService.categorizeTransaction(transactionDto.getDescription());

                // AI-powered fraud detection
                var fraudResponse = aiService.checkFraud(transactionDto.getDescription(),
                                transactionDto.getAmount().doubleValue());

                // Create transaction
                Transaction transaction = new Transaction();
                transaction.setType(transactionDto.getType());
                transaction.setAmount(transactionDto.getAmount());
                transaction.setDescription(transactionDto.getDescription());
                transaction.setCategory(category);
                transaction.setUser(user);
                transaction.setMerchantName(transactionDto.getMerchantName());
                transaction.setLocation(transactionDto.getLocation());
                transaction.setFraudRiskScore(fraudResponse.getRisk_score());
                transaction.setRiskLevel(fraudResponse.getRisk_level());

                Transaction savedTransaction = transactionRepository.save(transaction);

                // Update account balance
                userService.updateAccountBalance(
                                user.getId(),
                                transactionDto.getAmount(),
                                transactionDto.getType() == TransactionType.CREDIT);

                return mapToDto(savedTransaction);
        }

        public List<TransactionDto> getTransactionHistory(String username) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                return transactionRepository.findByUserIdOrderByTimestampDesc(user.getId())
                                .stream()
                                .map(this::mapToDto)
                                .collect(Collectors.toList());
        }

        public List<TransactionDto> filterByCategory(String username, TransactionCategory category) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                return transactionRepository.findByUserIdAndCategory(user.getId(), category)
                                .stream()
                                .map(this::mapToDto)
                                .collect(Collectors.toList());
        }

        public List<TransactionDto> filterByDateRange(String username, LocalDateTime start, LocalDateTime end) {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                return transactionRepository.findByUserIdAndTimestampBetween(user.getId(), start, end)
                                .stream()
                                .map(this::mapToDto)
                                .collect(Collectors.toList());
        }

        private TransactionDto mapToDto(Transaction transaction) {
                TransactionDto dto = new TransactionDto();
                dto.setId(transaction.getId());
                dto.setType(transaction.getType());
                dto.setAmount(transaction.getAmount());
                dto.setDescription(transaction.getDescription());
                dto.setCategory(transaction.getCategory());
                dto.setTimestamp(transaction.getTimestamp());
                dto.setMerchantName(transaction.getMerchantName());
                dto.setLocation(transaction.getLocation());
                dto.setFraudRiskScore(transaction.getFraudRiskScore());
                dto.setRiskLevel(transaction.getRiskLevel());
                return dto;
        }
}
