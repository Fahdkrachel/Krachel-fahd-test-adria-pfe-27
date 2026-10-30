package com.krachelfahd.transactionservice.service;
import com.krachelfahd.transactionservice.client.AccountRestClient;
import com.krachelfahd.transactionservice.domain.Transaction;
import com.krachelfahd.transactionservice.dto.AccountDTO;
import com.krachelfahd.transactionservice.dto.BalanceUpdateRequest;
import com.krachelfahd.transactionservice.dto.TransactionRequest;
import com.krachelfahd.transactionservice.dto.TransferRequest;
import com.krachelfahd.transactionservice.exception.BusinessException;
import com.krachelfahd.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountRestClient accountRestClient;

    @Value("${bank.transaction.max-amount}")
    private BigDecimal maxAmount;

    public TransactionService(TransactionRepository transactionRepository, AccountRestClient accountRestClient) {
        this.transactionRepository = transactionRepository;
        this.accountRestClient = accountRestClient;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Transaction not found : " + id));
    }

    public List<Transaction> getTransactionsByAccount(UUID accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Transactional
    public Transaction save(TransactionRequest request) {
        if (request.amount() == null || request.amount().signum() <= 0) {
            throw new BusinessException("Le montant doit etre strictement positif");
        }
        if (request.amount().compareTo(maxAmount) > 0) {
            throw new BusinessException("Le montant depasse le plafond autorise (" + maxAmount + ")");
        }

        AccountDTO account = checkAccount(request.accountId(), request.currency());

        BigDecimal balance = BigDecimal.valueOf(account.balance());
        if (balance.compareTo(request.amount()) < 0) {
            throw new BusinessException("Solde insuffisant sur le compte " + account.id());
        }

        Transaction saved = transactionRepository.save(Transaction.builder()
                .id(UUID.randomUUID())
                .accountId(account.id())
                .amount(request.amount())
                .currency(request.currency().toUpperCase())
                .merchantCategory(request.merchantCategory())
                .country(request.country())
                .createdAt(Instant.now())
                .build());


        accountRestClient.updateBalance(account.id(),
                new BalanceUpdateRequest(balance.subtract(request.amount()).doubleValue()));
        return saved;
    }

    @Transactional
    public Transaction transfer(TransferRequest request) {
        if (request.fromAccountId().equals(request.toAccountId())) {
            throw new BusinessException("Les comptes source et destination doivent etre differents");
        }

        AccountDTO target = checkAccount(request.toAccountId(), request.currency());


        Transaction debit = save(new TransactionRequest(request.fromAccountId(), request.amount(),
                request.currency(), "TRANSFER", "N/A"));


        accountRestClient.updateBalance(target.id(), new BalanceUpdateRequest(
                BigDecimal.valueOf(target.balance()).add(request.amount()).doubleValue()));
        return debit;
    }

    private AccountDTO checkAccount(UUID accountId, String currency) {
        AccountDTO account = accountRestClient.findAccountById(accountId);
        if (!"ACTIVE".equals(account.status())) {
            throw new BusinessException("Le compte " + accountId + " n'est pas actif");
        }
        if (currency == null || !account.currency().equalsIgnoreCase(currency)) {
            throw new BusinessException("La devise de la transaction (" + currency
                    + ") ne correspond pas a celle du compte (" + account.currency() + ")");
        }
        return account;
    }
}
