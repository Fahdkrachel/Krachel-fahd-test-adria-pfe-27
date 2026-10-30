package com.krachelfahd.accountservice.web.rest.rest;

import com.krachelfahd.accountservice.domain.Account;
import com.krachelfahd.accountservice.domain.AccountStatus;
import com.krachelfahd.accountservice.dto.BalanceUpdateRequest;
import com.krachelfahd.accountservice.dto.CreateAccountRequest;
import com.krachelfahd.accountservice.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountRestController {
    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}")
    public Account getAccountById(@PathVariable UUID id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("/accounts")
    public Account createAccount(@RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request.ownerName(), request.currency(), request.balance());
    }

    @PutMapping("/accounts/{id}/balance")
    public Account updateBalance(@PathVariable UUID id, @RequestBody BalanceUpdateRequest request) {
        return accountService.updateBalance(id, request.balance());
    }

    @PutMapping("/accounts/{id}/status")
    public Account updateStatus(@PathVariable UUID id, @RequestParam AccountStatus status) {
        return accountService.updateStatus(id, status);
    }
}
