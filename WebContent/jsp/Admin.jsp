<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="model.UserBean"%>
<%@page import="model.AuthorizedOwnerBean"%>
<%@page import="model.AuthorizedOwnerBeanDAO"%>
<%@page import="java.util.*"%>


<jsp:include page="header.jsp"></jsp:include>


<%
	//Redirecting to login if there isn't any user instance in session
	if (session.getAttribute("currentUser") == null) {
		response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
		log("no user in session!");
		return;
	} else {
		if (!((UserBean) session.getAttribute("currentUser")).getType().equals("admin")) {
			response.sendRedirect(request.getContextPath() + "/LoginForm.jsp");
			return;
		}
	}
%>
<%!private ArrayList<AuthorizedOwnerBean> getUsersToAuth() {
		AuthorizedOwnerBeanDAO retriever = new AuthorizedOwnerBeanDAO();
		return retriever.doRetrieveToBeAuthorized();
	}

	private String getRows() {
		ArrayList<AuthorizedOwnerBean> usersToAuth = getUsersToAuth();
		log("authsize:" + usersToAuth.size());
		String rows = "";
		for (AuthorizedOwnerBean toAuth : usersToAuth) {
			rows += "<tr><td>" + toAuth.getpIVA() + "</td><td>" + toAuth.getOwner() + "</td><td><div class='form-check ml-4  mb-4'><input class='form-check-input' type='radio' name='userChosen' onclick='setEmail(this)' value='"+toAuth.getOwner()+"'></div></td></tr>";
		}
		return rows;
	}%>

<div class="container-fluid">
	<div class="row d-flex justify-content-center">
		<div class=" card col-sm-12 col-md-6">
			<div class="card-body">
				<h4 class="card-title">Ristoratori da autorizzare</h4>
				<table>
					<thead>
						<tr>
							<th class="col">P.IVA</th>
							<th class="col">Email</th>
							<th class="col">Scegli</th>							
						</tr>
					</thead>
					<tbody>
						<%=getRows()%>
					</tbody>
				</table>
				<div class="form-row">
					<button onclick="acceptOwner()" class="btn btn-success mr-4">Accetta</button>
					<button onclick="rejectOwner()" class="btn btn-danger">Rifiuta</button>				
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/script/ownerValidation.js?ver="
	<%= Math.random()*2%>></script>
<jsp:include page="footer.jsp"></jsp:include>
