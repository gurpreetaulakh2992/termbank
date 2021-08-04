/**
 * 
 */
package com.groupA.termbank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groupA.termbank.dao.AccountDAO;
import com.groupA.termbank.entity.Account;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Transaction;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDAO;

	/**
	 * GetCustomerAccount Implementation.
	 */
	@Override
	public List<Account> getCustomerAccountList(int customerId) {

		List<Account> customerAccount = accountDAO.getCustomerAccountList(customerId);
		System.out.print(customerAccount.size());
		return customerAccount;
	}

	/**
	 * Get Account Transactions Implementations
	 */
	@Override
	public List<Transaction> getAccountTransactions(Integer accountId) {
		List<Transaction> transactionsList = accountDAO.getAccountTransactions(accountId);
		return transactionsList;
	}

	@Override
	public boolean transferFund(Customer sender, Transaction transaction) {
		return accountDAO.transferFund(sender, transaction);
	}

	@Override
	public boolean transferFundToSelf(Customer currentCustomer, Transaction transaction) {
		
		return accountDAO.transferFundToSelf(currentCustomer, transaction);
	}

	@Override
	public boolean depositMoney(Customer currentCustomer, Transaction transaction) {
		return accountDAO.depositMoney(currentCustomer, transaction);
	}

	@Override
	public boolean withdrawMoney(Customer currentCustomer, Transaction transaction) {
		return accountDAO.withdrawMoney(currentCustomer, transaction);
		
	}
	
	public boolean transferFundToOther(Customer c, Transaction t) {
		return accountDAO.transferFundToOther(c,t);
	}

	@Override
	public boolean processBill(Customer attribute, Transaction transaction) {
		return accountDAO.processBill(attribute,transaction);
	}

	
	



}
