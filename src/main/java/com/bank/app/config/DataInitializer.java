package com.bank.app.config;

import com.bank.app.model.Transaction;
import com.bank.app.model.TransactionCategory;
import com.bank.app.model.TransactionType;
import com.bank.app.model.User;
import com.bank.app.repository.TransactionRepository;
import com.bank.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByUsername("tester")) {
            return;
        }

        // Create Default User
        User user = new User();
        user.setUsername("tester");
        user.setEmail("tester@wio.com");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setFullName("Test User");
        user.setPhoneNumber("9876543210");
        user.setAccountBalance(new BigDecimal("15000.00"));
        user.setEnabled(true);
        user.setRole("USER");
        user = userRepository.save(user);

        // Seed some Transactions
        createTransaction(user, "Salary Credit", new BigDecimal("5000.00"), TransactionType.CREDIT,
                TransactionCategory.SALARY, "Company HR");
        createTransaction(user, "Starbucks Coffee", new BigDecimal("15.50"), TransactionType.DEBIT,
                TransactionCategory.FOOD, "Starbucks");
        createTransaction(user, "Grocery Shopping", new BigDecimal("120.00"), TransactionType.DEBIT,
                TransactionCategory.SHOPPING, "Walmart");
        createTransaction(user, "Zomato Dinner", new BigDecimal("45.00"), TransactionType.DEBIT,
                TransactionCategory.FOOD, "Zomato");
        createTransaction(user, "Electricity Bill", new BigDecimal("85.00"), TransactionType.DEBIT,
                TransactionCategory.BILLS, "Power Corp");
        createTransaction(user, "Netflix Subscription", new BigDecimal("19.99"), TransactionType.DEBIT,
                TransactionCategory.ENTERTAINMENT, "Netflix");

        System.out.println("✅ Database seeded with default user: tester / password123");
    }

    private void createTransaction(User user, String desc, BigDecimal amount, TransactionType type,
            TransactionCategory cat, String merchant) {
        Transaction tx = new Transaction();
        tx.setUser(user);
        tx.setDescription(desc);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setCategory(cat);
        tx.setMerchantName(merchant);
        tx.setTimestamp(LocalDateTime.now().minusDays((int) (Math.random() * 5)));
        tx.setFraudRiskScore(0.05);
        tx.setRiskLevel("LOW");
        transactionRepository.save(tx);
    }
}
