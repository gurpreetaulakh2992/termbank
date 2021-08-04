<%@ page import="com.groupA.termbank.entity.*, java.util.List" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@include file="common/header.jsp"%>
<%@include file="common/nav.jsp"%>
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
				     <c:forEach var = "view" items="${viewList}">
				        <jsp:include page="${view}" />
				     </c:forEach>
			</div>
		</div>

	</div>
</div>
<!-- /BODY ENDS HERE -->

<%@include file="common/footer.jsp"%>