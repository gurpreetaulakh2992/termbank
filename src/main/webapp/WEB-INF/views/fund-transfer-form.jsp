<%@page import="java.util.List, java.util.ArrayList, com.groupA.termbank.entity.*" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@include file="common/header.jsp"%>
	

<!-- BODY STARTS HERE -->
<div class="container" style="margin-top: 40px">
	<div class="row">
		
		<%@include file="common/admin-sidemenu.jsp"%>
		
		<%
			boolean isSelf = false;
			if(request.getAttribute("isSelf") != null) isSelf = true;
		%>

		<div class="col-sm-8 col-lg-9">
			<c:if test="${param.transfer eq 'failure'}">
				<div class="alert alert-primary">
					<strong>Oops!! Couldn't transfer. Please Try Again!!!</strong>
				</div>
			</c:if>

			<div class="jumbotron">
				<h2 class="display-4">Hello, ${customer.firstName}!</h2>
				<hr class="my-4">

				<div id="transferFund" class="row">
					<div class="col-sm-10 bg-light" style="padding: 15px; margin-top: 20px;margin-left:8%">

						<form:form  modelAttribute="transaction" action='<%=((isSelf)?"transfer-self":"fund-transfer-confirm")%>' method="post">
						 <div class="form-group">
						 <label>Select Account:</label>
						 <form:select path="accountId" name="accountId" class="form-control">
							<%
								List<Account> acList = (List<Account>)request.getAttribute("accountList");
								
								for(Account account : acList){
									
									out.print("<option value="+account.getAccountId()+">"+account.getAccountType()+" - "+account.getAccountNumber()+" - $("+account.getBalance()+")"+"</option>");
									
								}
								
							%>
							</form:select></div>
							<div class="form-group">
						
						<%
							if(isSelf){
								List<Account> acList = (List<Account>)request.getAttribute("accountList");
						%>
							 <label>To</label><br/>
							<form:select path='emailOrAccount' name='emailOrAccount' class='form-control'>
						<%
								for(Account account : acList){
						%>			
									<option value='<%=account.getAccountId() %>'><%=account.getAccountType() %>  - <%=account.getAccountNumber()%> - $(<%= account.getBalance()%> )</option>
						<%		
								}
						%>
								</form:select></div>
						<%	}else{
						%>
							<div class="form-group">
								<form:label  path="emailOrAccount" for="transferemail">Transfer via Email/Account Number:</form:label> 
								<form:input  path="emailOrAccount" type="text" name="email" class="form-control" placeholder="Enter Recipent Email" id="email" required="true"/>
							</div>
						<%} %>
							<div class="form-group">
								<form:label path="amount" for="amount">Enter Amount to Send:</form:label> <form:input
									path="amount" type="text" name="amount" class="form-control"
									placeholder="Enter Amount" id="amount" required="true"/>
							</div>

							<div class="form-group">
								<form:label path="remarks" for="remarks">Remarks</form:label> <form:input type="text"
									path="remarks" name="remarks" class="form-control" id="remarks"/>
							</div>

							<div class="form-check">
								<input type="checkbox" class="form-check-input" id="confirm"
									required="true"/> <label class="form-check-label" for="confirm">I
									Hereby Confirm to Send the Amount as Stated Above!</label>
							</div>
							
							
							<div class="form-group">
								<form:button class="btn btn-outline-primary">
									TRANSFER FUND NOW > ></form:button>

							</div>
						</form:form>

					</div>
				</div>


			</div>
		</div>

	</div>
</div>
<!-- /BODY ENDS HERE -->

<%@include file="common/footer.jsp"%>