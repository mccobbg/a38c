package com.a38c.eazybank.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a38c.eazybank.model.Cards;
import com.a38c.eazybank.repository.CardsRepository;

import lombok.AllArgsConstructor;
import java.util.List;

@RestController
@AllArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;

    @GetMapping("/cards")
    public List<Cards> getCardDetails(@RequestParam int id) {
        List<Cards> cards = cardsRepository.findByCustomerId(id);
        if (cards != null ) {
            return cards;
        }else {
            return null;
        }
    }

}
