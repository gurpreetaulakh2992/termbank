/**
 * 
 */
package com.groupA.termbank.service;

import java.util.List;

import com.groupA.termbank.entity.Account;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Transaction;

public interface AccountService {

	/**
	 * This method will return the Account Object for given customer
	 * 
	 * @param currCustomerId
	 * @return Account Object for a customer
	 */
	public List<Account> getCustomerAccountList(int customerId);

	/**
	 * This method will return all the transactions for given account id.
	 * 
	 * @param accountId
	 * @return List of Transactions for given account Id
	 */
	List<Transaction> getAccountTransactions(Integer accountId);

	/**
	 * This method will transfer the fund from one account to requested account. If
	 * fund transfer success return true else return false.
	 * 
	 * @param sender
	 * @param receiversAccountemail
	 * @param transaction
	 * @return True if, fund Transfer Successful
	 */
	boolean transferFund(Customer sender, Transaction transaction);

	public boolean transferFundToSelf(Customer currentCustomer, Transaction transaction);

	public boolean depositMoney(Customer currentCustomer, Transaction transaction);

	boolean withdrawMoney(Customer currentCustomer, Transaction transaction);
	
	public boolean transferFundToOther(Customer c, Transaction t);

	public boolean processBill(Customer attribute, Transaction transaction); 

}
