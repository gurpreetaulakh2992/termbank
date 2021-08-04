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
import org.springframework.stereotype.Repository;

import com.groupA.termbank.entity.Address;
import com.groupA.termbank.entity.Customer;
import com.groupA.termbank.entity.Utility;
@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private final String QUERY_LOGIN = "SELECT * FROM groupaproject.customer WHERE email=? AND password=?";

	private final String QUERY_ADDRESS = "SELECT * FROM groupaproject.address WHERE customerId=?";

	private final String UPDATE_ADDRESS = "update groupaproject.address SET line1=?, line2=?, city=?, state=?, zip=? WHERE customerId=?";

	/**
	 * This method will query for email and password, if found return the customer
	 * object else return null!!!
	 */
	@Override
	public Customer getCustomer(String email, String password) {
		Object[] dbQueryParam = new Object[] { email, password };
		return getCustomer(QUERY_LOGIN , dbQueryParam);
	}
	
	
	@Override
	public Customer getCustomer(String sql , Object[] dbQueryParam) {
		Customer customer = new Customer();
		try {

			customer = jdbcTemplate.queryForObject(sql, dbQueryParam, (resultSet, rowNum) -> {

				Customer customerfromDb = new Customer();

				customerfromDb.setCustomerId(resultSet.getInt("customerId"));
				customerfromDb.setFirstName(resultSet.getString("firstName"));
				customerfromDb.setLastName(resultSet.getString("lastName"));
				customerfromDb.setEmail(resultSet.getString("email"));
				customerfromDb.setRole(resultSet.getInt("role"));
				customerfromDb.setLoginStatus(resultSet.getInt("loginStatus"));

				return customerfromDb;

			});

		} catch (EmptyResultDataAccessException emptyData) {

			System.err.println("===== INSIDE CUSTOMERDAO: CUSTOMER NOT FOUND!! =====");
			customer = null;

		}

		return customer;
	}

	/**
	 * This method will query the database for ADDRESS QUERY and return the address
	 * object for customer.
	 */
	@Override
	public Address getCustomerAddress(int customerId) {

		Address address = new Address();
		Object[] queryParam = new Object[] { customerId };

		address = jdbcTemplate.queryForObject(QUERY_ADDRESS, queryParam, (resultSet, rowNum) -> {

			Address addressFromDB = new Address();

			addressFromDB.setLine1(resultSet.getString("line1"));
			addressFromDB.setLine2(resultSet.getString("line2"));
			addressFromDB.setCity(resultSet.getString("city"));
			addressFromDB.setState(resultSet.getString("state"));
			addressFromDB.setZip(resultSet.getString("zip"));

			return addressFromDB;

		});

		return address;
	}

	/**
	 * This method will update the customer's address and return the updated Address
	 */
	@Override
	public Address updateCustomerAddress(Address address, int customerId) {

		Object[] updateParams = new Object[] { address.getLine1(), address.getLine2(), address.getCity(),
				address.getState(), address.getZip(), customerId };

		int isUpdated = jdbcTemplate.update(UPDATE_ADDRESS, updateParams);
		
		if(isUpdated == 1)
			return address;
		
		else
			return null;
	}


	
	@Override
	public List<Utility> getUtilityList() {
		String sql = "select * from paybills_categories;";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Utility>>() {
				@Override
				public List<Utility> extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Utility> utitlityList = new ArrayList<Utility>();
					while (rs.next()) {
					
						Utility u = new Utility();
						u.setId(rs.getInt("id"));
						u.setName(rs.getString("account_particular"));
						
						utitlityList.add(u);
					}
					return utitlityList;
				}
			});
	}

}
