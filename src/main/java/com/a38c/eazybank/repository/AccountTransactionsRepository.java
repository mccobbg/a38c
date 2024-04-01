package com.a38c.eazybank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a38c.eazybank.model.AccountTransactions;

@Repository
public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions, Long> {
	
	List<AccountTransactions> findByUserIdOrderByTransactionDateDesc(String userId);

}
