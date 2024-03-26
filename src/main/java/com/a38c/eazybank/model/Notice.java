package com.a38c.eazybank.model;

import java.sql.Date;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "notice_details")
public class Notice {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name = "id")
	private int id;

	@Column(name = "notice_summary")
	private String noticeSummary;

	@Column(name = "notice_details")
	private String noticeDetails;

	@Column(name = "notice_begin_date")
	private Date noticeBeginDate;
	
	@Column(name = "notice_end_date")
	private Date noticeEndDate;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;

	public int getId() {
		return id;
	}

	public void setId(int noticeId) {
		this.id = noticeId;
	}

	public String getNoticeSummary() {
		return noticeSummary;
	}

	public void setNoticeSummary(String noticeSummary) {
		this.noticeSummary = noticeSummary;
	}

	public String getNoticeDetails() {
		return noticeDetails;
	}

	public void setNoticeDetails(String noticeDetails) {
		this.noticeDetails = noticeDetails;
	}

	public Date getNoticeBeginDate() {
		return noticeBeginDate;
	}

	public void setNoticeBeginDate(Date noticBegDt) {
		this.noticeBeginDate = noticBegDt;
	}

	public Date getNoticeEndDate() {
		return noticeEndDate;
	}

	public void setNoticeEndDate(Date noticEndDt) {
		this.noticeEndDate = noticEndDt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updateDt) {
		this.updatedAt = updateDt;
	}	
}
