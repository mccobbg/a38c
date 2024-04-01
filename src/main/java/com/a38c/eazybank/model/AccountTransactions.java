package com.a38c.eazybank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name="account_transactions")
public class AccountTransactions {
	
	@Id
	@Column(name = "transaction_id")
	private String transactionId;
	
	@Column(name="account_number")
	private long accountNumber;
	
	@NotNull
	@Column(name = "user_id")
	private String userId;
	
	@NotNull
	@Column(name="transaction_date")
	private Date transactionDate;
	
	@Column(name = "transaction_summary")
	private String transactionSummary;
	
	@Column(name="transaction_type")
	private String transactionType;
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "closing_balance")
	private float closingBalance;
	
	@Column(name = "created_at")
	private Date createdAt;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDt) {
		this.transactionDate = transactionDt;
	}

	public String getTransactionSummary() {
		return transactionSummary;
	}

	public void setTransactionSummary(String transactionSummary) {
		this.transactionSummary = transactionSummary;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float transactionAmt) {
		this.amount = transactionAmt;
	}

	public float getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(float closingBalance) {
		this.closingBalance = closingBalance;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
