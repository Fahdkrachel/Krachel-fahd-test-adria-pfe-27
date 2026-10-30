package com.krachelfahd.accountservice.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(UUID id) {
        super("Account not found : " + id);
    }
}
