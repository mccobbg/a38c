package com.a38c.eazybank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.a38c.eazybank.model.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
	
	// @Query(value = "from Notice n where DATE('now') BETWEEN noticeBeginDate AND noticeEndDate")
	// List<Notice> findAllActiveNotices();

	@NonNull List<Notice> findAll();

}
