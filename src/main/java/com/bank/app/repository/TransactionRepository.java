package com.bank.app.repository;

import com.bank.app.model.Transaction;
import com.bank.app.model.TransactionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

        List<Transaction> findByUserId(Long userId);

        List<Transaction> findByUserIdOrderByTimestampDesc(Long userId);

        List<Transaction> findByUserIdAndCategory(Long userId, TransactionCategory category);

        List<Transaction> findByUserIdAndTimestampBetween(Long userId, LocalDateTime start, LocalDateTime end);

        @Query("SELECT t.category, SUM(t.amount), COUNT(t) FROM Transaction t " +
                        "WHERE t.user.id = :userId AND t.type = 'DEBIT' " +
                        "GROUP BY t.category")
        List<Object[]> getCategoryWiseSpending(@Param("userId") Long userId);

        @Query("SELECT SUM(t.amount) FROM Transaction t " +
                        "WHERE t.user.id = :userId AND t.type = 'DEBIT' " +
                        "AND t.timestamp >= :startDate")
        BigDecimal getTotalSpending(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);

        @Query("SELECT SUM(t.amount) FROM Transaction t " +
                        "WHERE t.user.id = :userId AND t.type = 'CREDIT' " +
                        "AND t.timestamp >= :startDate")
        BigDecimal getTotalIncome(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
}
