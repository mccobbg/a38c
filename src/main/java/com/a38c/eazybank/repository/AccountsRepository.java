package com.a38c.eazybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a38c.eazybank.model.Accounts;
import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {
	
	List<Accounts> findByUserId(String userId);

}
