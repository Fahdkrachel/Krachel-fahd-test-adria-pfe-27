package com.krachelfahd.transactionservice.client;

import com.krachelfahd.transactionservice.dto.AccountDTO;
import com.krachelfahd.transactionservice.dto.BalanceUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "http://localhost:8056")
public interface AccountRestClient {
    @GetMapping("/accounts/{id}")
    AccountDTO findAccountById(@PathVariable String id);

    @PutMapping("/accounts/{id}/balance")
    AccountDTO updateBalance(@PathVariable String id, @RequestBody BalanceUpdateRequest request);
}
