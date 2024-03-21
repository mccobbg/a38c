package com.a38c.eazybank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.model.Accounts;
import com.a38c.eazybank.repository.AccountsRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;

    @GetMapping("/account")
    public Accounts getAccountDetails(@RequestParam int id) {
        Accounts accounts = accountsRepository.findByCustomerId(id);
        if (accounts != null ) {
            return accounts;
        }else {
            return null;
        }
    }

}
