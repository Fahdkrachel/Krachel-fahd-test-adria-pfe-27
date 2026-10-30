package com.krachelfahd.accountservice.dto;

public record CreateAccountRequest(String ownerName, String currency, double balance) {
}
