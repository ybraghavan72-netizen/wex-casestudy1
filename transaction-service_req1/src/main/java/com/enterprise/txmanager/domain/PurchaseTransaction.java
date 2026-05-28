package com.enterprise.txmanager.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

public record PurchaseTransaction(
    UUID id,
    String description,
    LocalDate transactionDate,
    BigDecimal amount
) {
    public PurchaseTransaction {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description must not exceed 50 characters.");
        }
        if (transactionDate == null) {
            throw new IllegalArgumentException("Transaction date is required.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Purchase amount must be a valid positive amount.");
        }
        // Strict accounting precision enforcement at the business boundary
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
}