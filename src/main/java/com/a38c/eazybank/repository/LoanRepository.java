package com.a38c.eazybank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a38c.eazybank.model.Loans;

@Repository
public interface LoanRepository extends JpaRepository<Loans, Long> {
	
	List<Loans> findByUserIdOrderByStartDateDesc(String userId);

}
