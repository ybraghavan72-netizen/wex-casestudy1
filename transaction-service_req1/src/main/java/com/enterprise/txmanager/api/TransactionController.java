package com.enterprise.txmanager.api;

import com.enterprise.txmanager.application.TransactionService;
import com.enterprise.txmanager.domain.PurchaseTransaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PurchaseTransaction> createTransaction(@RequestBody RequestDto request) {
        PurchaseTransaction inboundDomain = new PurchaseTransaction(
            null, // Identity is safely null until service generation triggers
            request.description(),
            request.transactionDate(),
            request.amount()
        );

        PurchaseTransaction result = service.processAndStore(inboundDomain);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    public record RequestDto(
        String description,
        LocalDate transactionDate,
        BigDecimal amount
    ) {}

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadBusinessInput(IllegalArgumentException ex) {
        return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), 
            HttpStatus.BAD_REQUEST
        );
    }

    public record ErrorResponse(int status, String errorReason) {}
}