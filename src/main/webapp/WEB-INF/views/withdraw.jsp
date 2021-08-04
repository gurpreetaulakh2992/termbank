<%@page
	import="java.util.List, java.util.ArrayList, com.groupA.termbank.entity.*"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="common/header.jsp"%>


<!-- BODY STARTS HERE -->
<div class="container" style="margin-top: 40px">
	<div class="row">

		<%@include file="common/admin-sidemenu.jsp"%>


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
					<div class="col-sm-10 bg-light"
						style="padding: 15px; margin-top: 20px; margin-left: 8%">
						<form:form modelAttribute="transaction" action="withdraw-money"
							method="post">
							<div class="form-group">
								<label>Select Account:</label><br />
								<form:select path="accountId" name="accountId"
									class="form-control">
									<%
									List<Account> acList = (List<Account>) request.getAttribute("accountList");

									for (Account account : acList) {

										out.print("<option value=" + account.getAccountId() + ">" + account.getAccountType() + " - "
										+ account.getAccountNumber() + " - $(" + account.getBalance() + ")" + "</option>");

									}
									%>
								</form:select>
							</div>
							<div class="form-group">
								<form:label path="amount" for="amount">Enter Amount to Withdraw:</form:label>
								<form:input path="amount" type="text" name="amount"
									class="form-control" placeholder="Enter Amount" id="amount"
									required="true" />
							</div>




							<div class="form-group">
								<form:button class="btn btn-outline-primary">
									WITHDRAW >></form:button>

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