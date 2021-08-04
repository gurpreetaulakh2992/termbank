/**
 * 
 */
package com.groupA.termbank.dao;

import java.util.List;

import com.groupA.termbank.entity.Account;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Transaction;

/**

 *
 */
public interface AccountDAO {

	/**
	 * This methods will return the account object containing all account
	 * information for given customer.
	 * 
	 * @param customerId
	 * @return Account Object for given customer
	 */
	List<Account> getCustomerAccountList(int customerId);
	/**
	 * This method will return the list of transactions for given account Id of
	 * customer
	 * 
	 * @param accountId
	 * @return List of Transactions for given account
	 */
	List<Transaction> getAccountTransactions(Integer accountId);

	
	boolean transferFundviaEmail();
	boolean transferFund(Customer sender, Transaction transaction);
	boolean transferFundToSelf(Customer currentCustomer, Transaction transaction);
	boolean depositMoney(Customer currentCustomer, Transaction transaction);
	boolean withdrawMoney(Customer currentCustomer, Transaction t);
	boolean transferFundToOther(Customer c, Transaction t);
	boolean processBill(Customer attribute, Transaction transaction);

	/**
	 * This method is returning the Account Object for Requested Customer using his
	 * CustomerId
	 */
	

}
