package com.krachelfahd.transactionservice.dto;

import java.util.UUID;

public record AccountDTO(UUID id, String customerReference, String currency, double balance, String status) {
}
