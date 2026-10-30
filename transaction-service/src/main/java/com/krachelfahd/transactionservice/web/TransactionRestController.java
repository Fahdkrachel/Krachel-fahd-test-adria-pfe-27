package com.krachelfahd.transactionservice.web;

import com.krachelfahd.transactionservice.domain.Transaction;
import com.krachelfahd.transactionservice.dto.TransactionRequest;
import com.krachelfahd.transactionservice.dto.TransferRequest;
import com.krachelfahd.transactionservice.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TransactionRestController {
    private TransactionService transactionService;

    public TransactionRestController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/transactions/account/{accountId}")
    public List<Transaction> getTransactionsByAccount(@PathVariable UUID accountId) {
        return transactionService.getTransactionsByAccount(accountId);
    }

    @PostMapping("/transactions")
    public Transaction save(@RequestBody TransactionRequest request) {
        return transactionService.save(request);
    }

    @PostMapping("/transactions/transfer")
    public Transaction transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }
}

