package com.enterprise.txmanager.infrastructure.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "purchase_transactions")
public class TransactionEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "description", length = 50, nullable = false)
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    protected TransactionEntity() {}

    public TransactionEntity(UUID id, String description, LocalDate transactionDate, BigDecimal amount) {
        this.id = id;
        this.description = description;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }

    public UUID getId() { return id; }
    public String getDescription() { return description; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public BigDecimal getAmount() { return amount; }
}