<%@ page import="com.groupA.termbank.entity.*, java.util.List" %>
<%@include file="common/header.jsp"%>
<!-- BODY STARTS HERE -->
<div class="container" style="margin-top: 40px">
	<div class="row">

		<%@include file="common/admin-sidemenu.jsp"%>

		<div class="col-sm-8 col-lg-9">
			<c:if test="${param.transfer eq 'success'}">
				<div class="alert alert-primary">
					<strong>Fund Transfer Success!!!</strong>
				</div>
			</c:if>
			<div class="jumbotron">
				<h3 class="display-4">Hello, ${customer.firstName}!</h3>
				<p class="lead">Your Account Details is:</p>
				<hr class="my-4">
				<% 
					List<Account> acList = (List<Account>) request.getAttribute("accountList");
				
					for(Account ac : acList){ 
				%>
						
					Account Number: <%=ac.getAccountNumber() %><br /> 
					Account Type:  <%=ac.getAccountType() %><br /> 
					Current Balance: $<%=ac.getBalance() %><br/><hr>
						
				<%
					}
				%>

				
				  
			</div>
		</div>

	</div>
</div>
<!-- /BODY ENDS HERE -->

<%@include file="common/footer.jsp"%>