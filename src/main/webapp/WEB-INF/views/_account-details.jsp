				<%@ page import="com.groupA.termbank.entity.*, java.util.List" %>
				<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
				<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
				<h3 class="display-4">Hello, ${customer.firstName}!</h3>
				<p class="lead">Your Account Details is:</p>
				<hr class="my-4">
				<% 
					List<Account> acList = (List<Account>) request.getAttribute("accountList");
				
					for(Account ac : acList){ 
				%>
						
					Account Number: <%=ac.getAccountNumber() %><br /> 
					Account Type:  <%=ac.getAccountType() %><br /> 
					Current Balance: <%=ac.getBalance() %><br/><hr>
						
				<%
					}
				%>
