package com.a38c.eazybank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

@Entity
@Table(name="accounts")
public class Accounts {

	@Id
	@Column(name="account_number")
	private long accountNumber;

	@NotNull
	@Column(name = "user_id")
	private String userId;

	@Column(name="account_type")
	private String accountType;

	@Column(name = "branch_address")
	private String branchAddress;

	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public Date getCreateAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
