/**
 * 
 */
package com.groupA.termbank.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import com.groupA.termbank.dao.CustomerDAO;
import com.groupA.termbank.entity.Address;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Utility;


@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDAO customerDAO;

	/**
	 * This method requests the DAO layer to get Information if customer is logged
	 * in or not
	 */
	@Override
	public Customer checkCustomerLogin(String email, String password) {

		Customer loggedInCustomer = customerDAO.getCustomer(email, password);
		return loggedInCustomer;

	}

	/**
	 * This method will return the address of customer for given id.
	 */
	@Override
	public Address getCustomerAddress(int customerId) {

		Address customerAddress = customerDAO.getCustomerAddress(customerId);
		return customerAddress;

	}

	/**
	 * This method will update the address and returns the updated address object
	 */
	@Override
	public Address updateCustomerAddress(Address address, int customerId) {
		Address updatedAddress = customerDAO.updateCustomerAddress(address, customerId);
		return updatedAddress;
	}

	@Override
	public Customer getCustomer(String sql, Object[] dbQueryParam) {
		return customerDAO.getCustomer(sql, dbQueryParam);
	}

	@Override
	public List<Utility> getUtilityList(){
		return customerDAO.getUtilityList();
	}


}
