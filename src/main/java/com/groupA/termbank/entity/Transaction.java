/**
 * 
 */
package com.groupA.termbank.entity;

public class Transaction {

	private Integer transactionId;
	private Double amount;
	private String transactionType;
	private String remarks;
	private java.sql.Date transactionDate;
	private int accountId;
	private String emailOrAccount;

	public String getEmailOrAccount() {
		return emailOrAccount;
	}



	public void setEmailOrAccount(String emailOrAccount) {
		this.emailOrAccount = emailOrAccount;
	}



	public int getAccountId() {
		return accountId;
	}



	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}



	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}



	public Transaction() {
	}
	
	

	public Transaction(Transaction transaction) {
		this.amount = transaction.getAmount();
		this.transactionType = transaction.getTransactionType();
		this.remarks = transaction.getRemarks();
		this.transactionDate = transaction.getTransactionDate();
	}



	/**
	 * @return the transactionId
	 */
	public int getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the transactionDate
	 */
	public java.sql.Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(java.sql.Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", amount=" + amount + ", transactionType="
				+ transactionType + ", remarks=" + remarks + ", transactionDate=" + transactionDate + "]";
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (transactionDate == null) {
			if (other.transactionDate != null)
				return false;
		} else if (!transactionDate.equals(other.transactionDate))
			return false;
		if (transactionId != other.transactionId)
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}
