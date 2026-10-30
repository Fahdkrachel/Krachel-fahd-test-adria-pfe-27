package com.krachelfahd.accountservice;

import com.krachelfahd.accountservice.domain.Account;
import com.krachelfahd.accountservice.domain.AccountStatus;
import com.krachelfahd.accountservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);

    }
    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(Account.builder().id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                    .customerReference("CUST-001").currency("MAD").balance(5000).status(AccountStatus.ACTIVE).build());
            accountRepository.save(Account.builder().id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                    .customerReference("CUST-002").currency("MAD").balance(2000).status(AccountStatus.ACTIVE).build());
            accountRepository.save(Account.builder().id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                    .customerReference("CUST-003").currency("EUR").balance(800).status(AccountStatus.ACTIVE).build());
            accountRepository.save(Account.builder().id(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                    .customerReference("CUST-004").currency("MAD").balance(100).status(AccountStatus.BLOCKED).build());
        };

    }

}
