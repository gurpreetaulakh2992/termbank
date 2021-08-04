/**
 * 
 */
package com.groupA.termbank.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.groupA.termbank.entity.Account;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Transaction;
import com.groupA.termbank.service.AccountService;


@Controller
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	/**
	 * This method will display the logged in customers basic account details like
	 * account number, current balance and account type.
	 * 
	 * @param session
	 * @return account-details view with Account Details
	 */
	@RequestMapping(value = "/user-dashboard/account-details")
	public String viewAccountDetails(HttpSession session, Model m) {

		if (session.getAttribute("customer") != null) {

			Customer currentCustomer = (Customer) session.getAttribute("customer");
			int currCustomerId = currentCustomer.getCustomerId();

			List<Account> customerAccount = accountService.getCustomerAccountList(currCustomerId);

			m.addAttribute("accountList", customerAccount);
			return "account-details";
		}

		else
			return "redirect:login";
	}

	/**
	 * This method will return the recent transactions of customer.
	 * 
	 * @param session
	 * @return Recent Transaction View Page
	 */
	@RequestMapping(value = "/user-dashboard/recent-transactions")
	public String viewRecentTransactions(HttpSession session) {

		if (session.getAttribute("customer") != null) {

			Customer currentCustomer = (Customer) session.getAttribute("customer");
			int currCustomerId = currentCustomer.getCustomerId();

			List<Transaction> accountTransactions = new ArrayList<Transaction>();

			if (accountTransactions != null)
				accountTransactions = getCustomerTransactions(currentCustomer.getCustomerId());

			currentCustomer.setTransactions(accountTransactions);

			return "account-transactions";

		}

		else
			return "redirect:login";

	}
	
	
	
//Fund transfer
	@GetMapping(value = "/user-dashboard/fund-transfer")
	public String showFundTransferForm(@ModelAttribute("transaction") Transaction t, Model m, HttpSession session, HttpServletRequest request) {
		
		addAccountList(m, session);
		
		if (session.getAttribute("customer") != null) {

			return "fund-transfer-form";

		} else
			return "redirect:login";

	}

	@PostMapping(value = "/user-dashboard/fund-transfer-confirm")
	public String transferFund(@ModelAttribute("transaction") Transaction transaction, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountListPost(model, session, transaction);

		boolean isTransferSuccess = accountService.transferFundToOther((Customer)session.getAttribute("customer"), transaction);

		if (isTransferSuccess) {
			model.addAttribute("transfer", "success");
			return "redirect:/user-dashboard/account-details";

		} else
			model.addAttribute("transfer", "failure");
		return "redirect:fund-transfer";

	}
	
	@GetMapping(value = "/user-dashboard/transfer-self")
	public String selfTransfer(@ModelAttribute("transaction") Transaction t, Model m, HttpSession session) {
		
		
		m.addAttribute("isSelf", true);
		int customerId = ((Customer)session.getAttribute("customer")).getCustomerId();
		
		List<Account> acList = accountService.getCustomerAccountList(customerId);
		m.addAttribute("accountList", acList);
		
		if (session.getAttribute("customer") != null) {

			return "fund-transfer-form";

		} else
			return "redirect:login";

	}
	
	@PostMapping(value = "/user-dashboard/transfer-self")
	public String processSelfTransfer(@ModelAttribute("transaction") Transaction t, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountListPost(model, session, t);

		boolean isTransferSuccess = accountService.transferFundToSelf((Customer) session.getAttribute("customer"), t);
		
		if (isTransferSuccess) {
			model.addAttribute("transfer", "success");
			return "redirect:/user-dashboard/account-details";

		} else
			model.addAttribute("transfer", "failure");
		return "redirect:/fund-transfer";

	}
	
	public void addAccountListPost(Model m, HttpSession session, Transaction t) {
		addAccountList(m, session);
		java.sql.Date transferDate = new java.sql.Date(new java.util.Date().getTime());
		t.setTransactionDate(transferDate);
	}
	
	
	@GetMapping(value = "/user-dashboard/withdraw-money")
	public String showWithdrawForm(@ModelAttribute("transaction") Transaction transaction, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountList(model, session);
		
		return "withdraw";

	}

	@GetMapping(value = "/user-dashboard/deposit-money")
	public String showDepositForm(@ModelAttribute("transaction") Transaction transaction, HttpServletRequest request,
			HttpSession session, Model model) {
		
		addAccountList(model, session);
		
		return "deposit";

	}
	
	@PostMapping(value = "/user-dashboard/deposit-money")
	public String processDeposit(@ModelAttribute("transaction") Transaction t, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountListPost(model, session, t);
		
		boolean isTransferSuccess = accountService.depositMoney((Customer) session.getAttribute("customer"), t);
		
		if(!isTransferSuccess) {
			model.addAttribute("transfer", "failure");
			return "redirect:fund-transfer";
		}
		
		model.addAttribute("transfer", "success");
		return "redirect:/user-dashboard/account-details";

	}
	
	@PostMapping(value = "/user-dashboard/withdraw-money")
	public String processWithdraw(@ModelAttribute("transaction") Transaction t, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountListPost(model, session, t);
		
		boolean isTransferSuccess = accountService.withdrawMoney((Customer) session.getAttribute("customer"), t);
		
		if(!isTransferSuccess) {
			model.addAttribute("transfer", "failure");
			return "redirect:fund-transfer";
		}
		
		model.addAttribute("transfer", "success");
		return "redirect:/user-dashboard/account-details";

	}
	
	public void addAccountList(Model m, HttpSession session) {
		int customerId = ((Customer)session.getAttribute("customer")).getCustomerId();
		List<Account> acList = accountService.getCustomerAccountList(customerId);
		m.addAttribute("accountList", acList);
		m.addAttribute("transaction", new Transaction());
	}
	
	private List<Transaction> getCustomerTransactions(Integer accountId) {
		return accountService.getAccountTransactions(accountId);
	}

	private Account setCustomerAccount(int currCustomerId) {
		return accountService.getCustomerAccountList(currCustomerId).get(0);
	}

}
