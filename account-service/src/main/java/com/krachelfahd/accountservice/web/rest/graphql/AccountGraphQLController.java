package com.krachelfahd.accountservice.web.rest.graphql;

import com.krachelfahd.accountservice.domain.Account;
import com.krachelfahd.accountservice.domain.AccountStatus;
import com.krachelfahd.accountservice.service.AccountService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;


@Controller
public class AccountGraphQLController {

    private AccountService accountService;

    public AccountGraphQLController(AccountService accountService) {
        this.accountService = accountService;
    }

    @QueryMapping
    public List<Account> accounts() {
        return accountService.getAllAccounts();
    }

    @QueryMapping
    public Account accountById(@Argument UUID id ) {
        return accountService.getAccountById(id);
    }

    @MutationMapping
    public Account createAccount(@Argument String ownerName, @Argument String currency, @Argument double balance) {
        return accountService.createAccount(ownerName, currency, balance);
    }

    @MutationMapping
    public Account updateAccountStatus(@Argument UUID id, @Argument AccountStatus status) {
        return accountService.updateStatus(id, status);
    }
}
