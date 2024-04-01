package com.a38c.eazybank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.model.Accounts;
import com.a38c.eazybank.repository.AccountsRepository;
import java.util.List;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AccountsController {

    private final AccountsRepository accountsRepository;

    @GetMapping("/accounts")
    public List<Accounts> getAccounts(@RequestParam String userId) {
        List<Accounts> accounts = accountsRepository.findByUserId(userId);
        if (accounts != null ) {
            return accounts;
        }else {
            return null;
        }
    }

}
