/**
 * 
 */
package com.groupA.termbank.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.groupA.termbank.entity.Account;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Transaction;
import com.groupA.termbank.service.AccountService;
import com.groupA.termbank.service.CustomerService;

@Repository
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	CustomerService customerService;

	private final String QUERY_ACCOUNT = "SELECT * FROM groupaproject.account WHERE customerId=?";

	private final String QUERY_TRANSACTIONS = "select * from transaction t inner join account a on a.accountId=t.accountId"
			+ "	inner join customer c on a.customerId = c.customerId where c.customerId=? order by t.transactionId desc";

	/**
	 * This method is returning the Account Object for Requested Customer using his
	 * CustomerId
	 */
	@Override
	public List<Account> getCustomerAccountList(int customerId) {

		return jdbcTemplate.query("SELECT * FROM groupaproject.account WHERE customerId=" + customerId,
				new RowMapper<Account>() {
					@Override
					public Account mapRow(ResultSet rs, int rownumber) throws SQLException {
						Account a = new Account();
						a.setAccountId(rs.getInt("accountId"));
						a.setAccountNumber(rs.getString("accountNumber"));
						a.setBalance(rs.getDouble("balance"));
						a.setAccountType(rs.getString("accountType"));
						return a;
					}
				});

	}

	
	public Account getAccount(String sql) {

		return jdbcTemplate.query(sql, new ResultSetExtractor<Account>() {
			@Override
			public Account extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				if (!rs.next()) return null;
				
				Account ac = new Account();
				ac.setAccountId(rs.getInt("accountId"));
//			        	ac.setEmpName(rs.getString("name"));
//			        	ac.setAge(rs.getInt("age"));
				
				return ac;
			}
		});
	}

	/**
	 * This method is returning List of Transactions for given accountNumber
	 */
	@Override
	public List<Transaction> getAccountTransactions(Integer customerId) {

		List<Transaction> transactions = jdbcTemplate.query(QUERY_TRANSACTIONS, new Object[] { customerId },
				(resultSet, rowNum) -> {

					Transaction transaction = new Transaction();

					transaction.setTransactionId(resultSet.getInt("transactionId"));
					transaction.setAmount(resultSet.getDouble("amount"));
					transaction.setTransactionType(resultSet.getString("transactionType"));
					transaction.setRemarks(resultSet.getString("remarks"));
					transaction.setTransactionDate(resultSet.getDate("transactionDate"));

					return transaction;
				});

		return transactions;

	}

	@Override
	public boolean transferFundviaEmail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean transferFund(Customer sender, Transaction t) {
		return saveTransaction(t, "By email");
	}

	public boolean updateBalance(int accountId, double amount) {
		if (jdbcTemplate
				.update("update Account set balance=balance+(" + amount + ") where accountId=" + accountId + ";") == 1)
			return true;
		return false;
	}

	@Override
	public boolean transferFundToSelf(Customer c, Transaction t) {

		if (!updateBalance(t.getAccountId(), -t.getAmount()))
			return false;

		updateBalance(Integer.parseInt(t.getEmailOrAccount()), t.getAmount());

		return saveTransaction(t, "Self transfer");
	}

	@Override
	public boolean transferFundToOther(Customer c, Transaction t) {

		System.out.println("In transferFundToOther");

		Account ac = getAccount(
				"select * from customer c inner join account a on a.customerId=c.customerId where email='"+t.getEmailOrAccount()+"' or accountNumber = '"+t.getEmailOrAccount()+"'");

		if (ac == null) {
			System.out.println("In transferFundToOther: ac is null");
			return false;
		}
		System.out.print(ac.getAccountId());
		if (!updateBalance(t.getAccountId(), -t.getAmount()))
			return false;

		updateBalance(ac.getAccountId(), t.getAmount());
		System.out.print("transfer Success");
		return saveTransaction(t, "Transfer Within Bank");
	}

	public boolean saveTransaction(Transaction t, String tType) {
		int effectedRows = jdbcTemplate
				.update("Insert into Transaction (amount, transactionType, remarks, transactionDate, accountId, email)"
						+ " values ('" + t.getAmount() + "', '" + tType + "', '" + t.getRemarks() + "', '"
						+ t.getTransactionDate() + "', '" + t.getAccountId() + "','" + t.getEmailOrAccount() + "')");

		if (effectedRows == 1) {
			System.out.println("Transaction successfull!");
			return true;
		}
		return false;
	}

	@Override
	public boolean depositMoney(Customer currentCustomer, Transaction t) {

		if (!updateBalance(t.getAccountId(), t.getAmount()))
			return false;
		return saveTransaction(t, "Deposit");
	}

	@Override
	public boolean withdrawMoney(Customer currentCustomer, Transaction t) {
		if (!updateBalance(t.getAccountId(), -t.getAmount()))
			return false;
		return saveTransaction(t, "Withdraw");
	}


	@Override
	public boolean processBill(Customer c, Transaction t) {
		if (!updateBalance(t.getAccountId(), -t.getAmount()))
			return false;
		return saveTransaction(t, "Bill payment");
	}

}
