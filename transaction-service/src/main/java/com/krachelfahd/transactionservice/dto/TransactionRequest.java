package com.krachelfahd.transactionservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(UUID accountId, BigDecimal amount, String currency, String merchantCategory, String country) {
}
