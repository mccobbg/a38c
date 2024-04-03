package com.a38c.eazybank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a38c.eazybank.model.Cards;

@Repository
public interface CardsRepository extends JpaRepository<Cards, Long> {
	
	List<Cards> findByUserId(String userId);

}
