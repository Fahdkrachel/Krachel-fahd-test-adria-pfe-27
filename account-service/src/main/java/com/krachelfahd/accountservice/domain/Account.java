package com.krachelfahd.accountservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Account {
    @Id
    private UUID id;
    private String customerReference;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private double balance;
}
