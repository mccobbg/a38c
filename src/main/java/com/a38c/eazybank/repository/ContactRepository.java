package com.a38c.eazybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a38c.eazybank.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	
}
