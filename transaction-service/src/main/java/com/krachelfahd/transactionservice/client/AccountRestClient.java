package com.krachelfahd.transactionservice.client;

import com.krachelfahd.transactionservice.dto.AccountDTO;
import com.krachelfahd.transactionservice.dto.BalanceUpdateRequest;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@FeignClient(name = "account-service", url = "http://localhost:8056")
public interface AccountRestClient {
    @GetMapping("/accounts/{id}")
    @CircuitBreaker(name = "account-service", fallbackMethod = "accountFallback")
    AccountDTO findAccountById(@PathVariable String id);

    @PutMapping("/accounts/{id}/balance")
    @CircuitBreaker(name = "account-service", fallbackMethod = "balanceFallback")
    AccountDTO updateBalance(@PathVariable String id, @RequestBody BalanceUpdateRequest request);

    default AccountDTO accountFallback(String id, Exception exception) {
        // pour afficher aux administrateurs l'exception
        exception.printStackTrace();
        if (exception instanceof FeignException.NotFound) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Compte introuvable : " + id);
        }
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Account-Service indisponible, veuillez reessayer plus tard");
    }

    default AccountDTO balanceFallback(String id, BalanceUpdateRequest request, Exception exception) {
        exception.printStackTrace();
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Account-Service indisponible : le solde n'a pas pu etre mis a jour");
    }
}
