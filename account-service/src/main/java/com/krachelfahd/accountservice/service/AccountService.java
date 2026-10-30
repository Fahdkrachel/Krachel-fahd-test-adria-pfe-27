package com.krachelfahd.accountservice.service;

import com.krachelfahd.accountservice.domain.Account;
import com.krachelfahd.accountservice.domain.AccountStatus;
import com.krachelfahd.accountservice.exception.AccountNotFoundException;
import com.krachelfahd.accountservice.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account getAccountById(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));}

    public Account createAccount(String customerReference, String currency, double balance) {
        return accountRepository.save(Account.builder()
                .id(UUID.randomUUID())
                .customerReference(customerReference)
                .currency(currency.toUpperCase())
                .balance(balance)
                .status(AccountStatus.ACTIVE)
                .build());
    }
    public Account updateBalance(UUID id, double newBalance) {
        Account account = getAccountById(id);
        account.setBalance(newBalance);
        return accountRepository.save(account);
    }

    public Account updateStatus(UUID id, AccountStatus status) {
        Account account = getAccountById(id);
        account.setStatus(status);
        return accountRepository.save(account);
    }
}
