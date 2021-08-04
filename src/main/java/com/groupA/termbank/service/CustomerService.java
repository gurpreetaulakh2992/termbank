/**
 * 
 */
package com.groupA.termbank.service;

import java.util.List;

import com.groupA.termbank.entity.Address;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Utility;

/**
 * @author AsimSubedi
 *
 */
public interface CustomerService {

	/**
	 * This method handles the login operation using the credentials. If user is
	 * present, return customer object, else return null
	 * 
	 * @param email    of customer
	 * @param password of customer
	 * @return loggedInCustomer
	 */
	Customer checkCustomerLogin(String email, String password);

	/**
	 * This method will return the Address Object of requested customer based on his
	 * Id.
	 * 
	 * @param currCustomerId
	 * @return Address of Requested Customer based on his Id.
	 */
	Address getCustomerAddress(int currCustomerId);

	/**
	 * This method will update the customer's address and return the updated address
	 * 
	 * @param address
	 * @param customerId 
	 * @return updatedAddressObject for customer
	 */
	Address updateCustomerAddress(Address address, int customerId);
	
	Customer getCustomer(String sql, Object[] dbQueryParam);

	List<Utility> getUtilityList();

}
