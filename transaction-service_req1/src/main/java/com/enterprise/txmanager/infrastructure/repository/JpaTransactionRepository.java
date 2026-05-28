package com.enterprise.txmanager.infrastructure.repository;

import com.enterprise.txmanager.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaTransactionRepository extends JpaRepository<TransactionEntity, UUID> {}