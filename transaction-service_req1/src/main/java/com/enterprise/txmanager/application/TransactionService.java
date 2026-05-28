package com.enterprise.txmanager.application;

import com.enterprise.txmanager.domain.PurchaseTransaction;
import com.enterprise.txmanager.infrastructure.entity.TransactionEntity;
import com.enterprise.txmanager.infrastructure.repository.JpaTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionService {

    private final JpaTransactionRepository repository;

    public TransactionService(JpaTransactionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PurchaseTransaction processAndStore(PurchaseTransaction domainTx) {
        // Enforce internal business identity creation rules natively
        UUID generationId = UUID.randomUUID();
        
        PurchaseTransaction persistentDomain = new PurchaseTransaction(
            generationId, 
            domainTx.description(), 
            domainTx.transactionDate(), 
            domainTx.amount()
        );

        TransactionEntity entity = new TransactionEntity(
            persistentDomain.id(),
            persistentDomain.description(),
            persistentDomain.transactionDate(),
            persistentDomain.amount()
        );

        repository.save(entity);
        return persistentDomain;
    }
}