/**
 * 
 */
package com.groupA.termbank.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;

import com.groupA.termbank.entity.Account;
import com.groupA.termbank.entity.Address;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Transaction;
import com.groupA.termbank.entity.Utility;
import com.groupA.termbank.service.AccountService;
import com.groupA.termbank.service.CustomerService;

/**
 * CustomerController class basically responsible to handle the login and
 * displaying dashboard if loggedin or redirect to login if unable to verify.
 * 
 * @author 
 *
 */
@Controller
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AccountService accountService;

	/**
	 * This method displays the Login Page.
	 * 
	 * @return login page
	 */
	@RequestMapping(value = { "/", "/login" })
	public String displayLoginPage() {

		return "login";

	}

	/**
	 * This method handles the user login credentials. If user is present, show him
	 * dashboard, else redirect to login page.
	 * 
	 * @param request
	 * @param model
	 * @param session
	 * @return user-dashboard page if login successful
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String handleCustomerLogin(HttpServletRequest request, Model model, HttpSession session) {

		String viewName = "";

		Customer loggedInCustomer = customerService.checkCustomerLogin(request.getParameter("email"),
				request.getParameter("password"));

		if (loggedInCustomer != null) {
			session.setAttribute("customer", loggedInCustomer);
			viewName = "redirect:user-dashboard";

		} else {

			model.addAttribute("errorMsg", "Sorry! Couldn't Login! Try Again!!");
			viewName = "login";
		}

		return viewName;

	}

	/**
	 * This method is responsible to show the user dashboard view. Only the loggedIn
	 * user can view this page.
	 * 
	 * @param session
	 * @return user-dashboard view
	 */
	@RequestMapping(value = "/user-dashboard")
	public String showUserDashboard(HttpSession session) {

		if (session.getAttribute("customer") != null)
			return "user-dashboard";

		else
			return "redirect:login";

	}

	/**
	 * This method is responsible to logout the user by invalidating the sessions.
	 * After logout user is redirected to login page.
	 * 
	 * @param request
	 * @param session
	 * @return login-page view
	 */
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		session.invalidate();
		session = request.getSession(true);
		return "redirect:login?action=logout";

	}

	/**
	 * This method is responsible to show the address of loggedIn customer
	 * 
	 * @param session
	 * @return view-address view
	 */
	@RequestMapping(value = "user-dashboard/view-address")
	public String viewCustomerAddress(HttpSession session) {

		if (session.getAttribute("customer") != null) {

			Customer currentCustomer = (Customer) session.getAttribute("customer");
			int currCustomerId = currentCustomer.getCustomerId();

			Address customerAddress = currentCustomer.getAddress();

			if (customerAddress == null)
				customerAddress = customerService.getCustomerAddress(currCustomerId);

			currentCustomer.setAddress(customerAddress);

			return "view-address";
		}

		else
			return "redirect:login";

	}

	/**
	 * This method will update the address of customer and return address view with
	 * update information.
	 * 
	 * @param address
	 * @param session
	 * @param model
	 * @return address view with update information
	 */
	@RequestMapping(value = "user-dashboard/update-address", method=RequestMethod.POST)
	public String updateCustomerAddress(@ModelAttribute("address") Address address, HttpSession session, Model model) {

		Customer customer = (Customer) session.getAttribute("customer");
		
		int customerId = customer.getCustomerId();

		Address updatedAddress = customerService.updateCustomerAddress(address, customerId);

		// if successfully updated without error
		if (updatedAddress != null) {
			customer.setAddress(updatedAddress);

		}

		return "redirect:view-address";

	}
	
	@GetMapping(value = "/user-dashboard/pay-bills")
	public String showPaybillsForm(@ModelAttribute("transaction") Transaction transaction, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountUtilityList(model , session);
		
		return "pay-bills";

	}
	
	@PostMapping(value = "/user-dashboard/pay-bills")
	public String processPayBills(@ModelAttribute("transaction") Transaction transaction, HttpServletRequest request,
			HttpSession session, Model model) {

		addAccountListPost(model , session, transaction);
		
		// process bill
		boolean isTransferSuccess = accountService.processBill((Customer) session.getAttribute("customer"), transaction);
		
		if (isTransferSuccess) {
			model.addAttribute("transfer", "success");
			return "redirect:/user-dashboard/account-details";

		} else
			model.addAttribute("transfer", "failure");
		
		
		return "redirect:/user-dashboard";

	}
	
	private void addAccountUtilityList(Model m, HttpSession session) {
		int customerId = ((Customer)session.getAttribute("customer")).getCustomerId();
		List<Utility> uList = customerService.getUtilityList();
		m.addAttribute("utilityList", uList);
		List<Account> acList = accountService.getCustomerAccountList(customerId);
		m.addAttribute("accountList", acList);
		m.addAttribute("transaction", new Transaction());
	}

	public void addAccountListPost(Model m, HttpSession session, Transaction t) {
		addAccountUtilityList(m, session);
		java.sql.Date transferDate = new java.sql.Date(new java.util.Date().getTime());
		t.setTransactionDate(transferDate);
	}

}
