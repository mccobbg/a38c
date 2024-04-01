package com.a38c.eazybank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.model.Loans;
import com.a38c.eazybank.repository.LoanRepository;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loans> getLoanDetails(@RequestParam String userId) {
        List<Loans> loans = loanRepository.findByUserIdOrderByStartDateDesc(userId);
        if (loans != null ) {
            return loans;
        }else {
            return null;
        }
    }

}
