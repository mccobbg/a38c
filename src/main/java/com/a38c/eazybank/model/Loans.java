package com.a38c.eazybank.model;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="loans")
public class Loans {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	@Column(name = "loan_number")
	private long loanNumber;
	
	@NotNull
	@Column(name = "user_id")
	private String userId;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name = "loan_type")
	private String loanType;
	
	@Column(name = "total_loan")
	private float totalLoan;
	
	@Column(name = "amount_paid")
	private float amountPaid;
	
	@Column(name = "outstanding_amount")
	private float outstandingAmount;
	
	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "updated_at")
	private Date updatedAt;

	public long getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(long loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDt(Date startDate) {
		this.startDate = startDate;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public float getTotalLoan() {
		return totalLoan;
	}

	public void setTotalLoan(float totalLoan) {
		this.totalLoan = totalLoan;
	}

	public float getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(float amountPaid) {
		this.amountPaid = amountPaid;
	}

	public float getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(float outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreateDt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
