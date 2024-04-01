package com.a38c.eazybank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.model.AccountTransactions;
import com.a38c.eazybank.repository.AccountTransactionsRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@AllArgsConstructor
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;

    @GetMapping("/balance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String userId) {
        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByUserIdOrderByTransactionDateDesc(userId);
        if (accountTransactions != null ) {
            return accountTransactions;
        }else {
            return null;
        }
    }
}
